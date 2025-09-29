package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Kitchen;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public class KitchenService {
    private Queue<Order> filaEntrada;
    private Order emPreparacao;
    private Queue<Order> filaSaida;

    private ScheduledExecutorService scheduler;

    public KitchenService() {
        filaEntrada = new LinkedBlockingQueue<Order>();
        emPreparacao = null;
        filaSaida = new LinkedBlockingQueue<Order>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    private synchronized void colocaEmPreparacao(Order pedido){
    pedido.setStatus(Order.Status.PREPARATION);
        emPreparacao = pedido;
        System.out.println("Pedido em preparacao: "+pedido);
        // Agenda pedidoPronto para ser chamado em 2 segundos
        scheduler.schedule(() -> pedidoPronto(), 5, TimeUnit.SECONDS);
    }

    public synchronized void chegadaDePedido(Order p) {
        filaEntrada.add(p);
        System.out.println("Pedido na fila de entrada: "+p);
        if (emPreparacao == null) {
            colocaEmPreparacao(filaEntrada.poll());
        }
    }

    public synchronized void pedidoPronto() {
        emPreparacao.setStatus(Order.Status.READY);
        filaSaida.add(emPreparacao);
        System.out.println("Pedido na fila de saida: "+emPreparacao);
        emPreparacao = null;
        // Se tem pedidos na fila, programa a preparação para daqui a 1 segundo
        if (!filaEntrada.isEmpty()){
            Order prox = filaEntrada.poll();
            scheduler.schedule(() -> colocaEmPreparacao(prox), 1, TimeUnit.SECONDS);
        }
    }
}
