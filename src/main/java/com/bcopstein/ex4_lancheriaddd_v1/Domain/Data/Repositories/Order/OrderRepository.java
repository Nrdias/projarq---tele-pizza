package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public interface OrderRepository {
  public Order getOrder(long id);
  public Order createOrder(Order order);
  public long getNextOrderId();
  public boolean cancelOrder(long id);
}
