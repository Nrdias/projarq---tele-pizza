package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Item.ItemRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Kitchen.KitchenService;

@Service
public class OrderService {
  private final OrderRepository pedidoRepository;
  private final ItemRepository itemRepository;
  private final KitchenService kitchenService;

  @Autowired
  public OrderService(OrderRepository pedidoRepository, 
                     ItemRepository itemRepository,
                     KitchenService kitchenService) {
    this.pedidoRepository = pedidoRepository;
    this.itemRepository = itemRepository;
    this.kitchenService = kitchenService;
  }

  public Order getOrder(long id){
    return pedidoRepository.getOrder(id);
  }

  public List<OrderItem> getOrderItems(long id){
    return itemRepository.getOrderItems(id);
  }

  public boolean cancelOrder(Order order) {
    return pedidoRepository.updateOrderStatus(order.getId(), Order.Status.CANCELADO);
  }

  public boolean processPayment(long orderId, LocalDateTime paymentDateTime) {
    try {
      boolean paymentUpdated = pedidoRepository.updatePaymentDate(orderId, paymentDateTime);
      if (!paymentUpdated) {
        return false;
      }
      
      boolean statusUpdated = pedidoRepository.updateOrderStatus(orderId, Order.Status.PAGO);
      if (!statusUpdated) {
        return false;
      }
      
      Order paidOrder = pedidoRepository.getOrder(orderId);
      if (paidOrder != null) {
        kitchenService.chegadaDePedido(paidOrder);
      }
      
      return true;
    } catch (Exception e) {
      System.err.println("Error processing payment for order " + orderId + ": " + e.getMessage());
      return false;
    }
  }
}
