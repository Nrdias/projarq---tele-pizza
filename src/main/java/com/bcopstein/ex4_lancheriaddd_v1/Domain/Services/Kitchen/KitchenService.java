package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Kitchen;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Delivery.DeliveryService;

@Service
public class KitchenService {
    private final Queue<Order> filaEntrada;
    private Order emPreparacao;
    private final Queue<Order> filaSaida;
    private final ScheduledExecutorService scheduler;
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;

    @Autowired
    public KitchenService(DeliveryService deliveryService, OrderRepository orderRepository) {
        this.filaEntrada = new LinkedBlockingQueue<>();
        this.emPreparacao = null;
        this.filaSaida = new LinkedBlockingQueue<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.deliveryService = deliveryService;
        this.orderRepository = orderRepository;
    }

    private synchronized void colocaEmPreparacao(Order pedido){
        pedido.setStatus(Order.Status.PREPARACAO);
        orderRepository.updateOrderStatus(pedido.getId(), Order.Status.PREPARACAO);
        emPreparacao = pedido;
        System.out.println("Pedido em preparacao: " + pedido);
        scheduler.schedule(this::pedidoPronto, 5, TimeUnit.SECONDS);
    }

    public synchronized void chegadaDePedido(Order p) {
        filaEntrada.add(p);
        System.out.println("Pedido na fila de entrada da cozinha: " + p);
        if (emPreparacao == null) {
            colocaEmPreparacao(filaEntrada.poll());
        }
    }

    public synchronized void pedidoPronto() {
        if (emPreparacao != null) {
            emPreparacao.setStatus(Order.Status.PRONTO);
            orderRepository.updateOrderStatus(emPreparacao.getId(), Order.Status.PRONTO);
            filaSaida.add(emPreparacao);
            System.out.println("Pedido pronto na cozinha: " + emPreparacao);
            
            deliveryService.receiveOrderFromKitchen(emPreparacao);
            
            emPreparacao = null;
            
            if (!filaEntrada.isEmpty()) {
                Order prox = filaEntrada.poll();
                scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
            }
        }
    }
    public synchronized int getQueueSize() {
        return filaEntrada.size();
    }

    public synchronized boolean isPreparationInProgress() {
        return emPreparacao != null;
    }

    public synchronized Order getCurrentOrderInPreparation() {
        return emPreparacao;
    }
}
