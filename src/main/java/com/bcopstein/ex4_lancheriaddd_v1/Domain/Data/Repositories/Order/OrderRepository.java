package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order;

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public interface OrderRepository {
  public Order getOrder(long id);
  public Order createOrder(Order order);
  public long getNextOrderId();
  public List<Order> getAllOrdersPaginated(int page, int size);
  public long getTotalOrdersCount();
}
