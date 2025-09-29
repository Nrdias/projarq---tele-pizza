package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Component
public class GetOrderUsecase {
  private OrderRepository orderRepository;

  @Autowired
  public GetOrderUsecase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order run(long id) {
    return orderRepository.getOrder(id);
  }
}
