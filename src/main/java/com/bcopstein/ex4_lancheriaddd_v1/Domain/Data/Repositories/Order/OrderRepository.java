package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order;

import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order.Status;

public interface OrderRepository {
  public Order getOrder(long id);
  public Order createOrder(Order order);
  public long getNextOrderId();
  public boolean updateOrderStatus(long id, Status newStatus);
  public boolean updatePaymentDate(long id, LocalDateTime paymentDateTime);
  public List<Order> getDeliveredOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
  public List<Order> getCustomerDeliveredOrdersBetweenDates(String customerCpf, LocalDateTime startDate, LocalDateTime endDate);
}
