package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order;

import java.time.LocalDateTime;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order.Status;

public interface OrderRepository {
  public Order getOrder(long id);
  public Order createOrder(Order order);
  public long getNextOrderId();
  public boolean updateOrderStatus(long id, Status newStatus);
  public boolean updatePaymentDate(long id, LocalDateTime paymentDateTime);
}
