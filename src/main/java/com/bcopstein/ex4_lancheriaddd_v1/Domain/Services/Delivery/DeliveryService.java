package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Delivery;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Service
public class DeliveryService {
    
    private final Queue<Order> deliveryQueue;
    private Order orderInTransit;
    private final Queue<Order> deliveredOrders;
    private final ScheduledExecutorService scheduler;
    private final OrderRepository orderRepository;

    @Autowired
    public DeliveryService(OrderRepository orderRepository) {
        this.deliveryQueue = new LinkedBlockingQueue<>();
        this.orderInTransit = null;
        this.deliveredOrders = new LinkedBlockingQueue<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.orderRepository = orderRepository;
    }


    public synchronized void receiveOrderFromKitchen(Order order) {
        deliveryQueue.add(order);
        order.setStatus(Order.Status.AGUARDANDO);
        orderRepository.updateOrderStatus(order.getId(), Order.Status.AGUARDANDO);
        System.out.println("Pedido recebido da cozinha e aguardando entrega: " + order);
        
        if (orderInTransit == null) {
            startNextDelivery();
        }
    }

    private synchronized void startNextDelivery() {
        if (!deliveryQueue.isEmpty() && orderInTransit == null) {
            orderInTransit = deliveryQueue.poll();
            orderInTransit.setStatus(Order.Status.TRANSPORTE);
            orderRepository.updateOrderStatus(orderInTransit.getId(), Order.Status.TRANSPORTE);
            System.out.println("Pedido saiu para entrega: " + orderInTransit);
            
            scheduler.schedule(this::completeDelivery, 10, TimeUnit.SECONDS);
        }
    }

    private synchronized void completeDelivery() {
        if (orderInTransit != null) {
            orderInTransit.setStatus(Order.Status.ENTREGUE);
            orderRepository.updateOrderStatus(orderInTransit.getId(), Order.Status.ENTREGUE);
            deliveredOrders.add(orderInTransit);
            System.out.println("Pedido entregue com sucesso: " + orderInTransit);
            orderInTransit = null;
            
            if (!deliveryQueue.isEmpty()) {
                scheduler.schedule(this::startNextDelivery, 2, TimeUnit.SECONDS);
            }
        }
    }

    public synchronized int getQueueSize() {
        return deliveryQueue.size();
    }

    public synchronized boolean isDeliveryInProgress() {
        return orderInTransit != null;
    }

    public synchronized Order getCurrentOrderInTransit() {
        return orderInTransit;
    }
}