package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Service
public class OrderService {
  private OrderRepository pedidoRepository;

  @Autowired
  public OrderService(OrderRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  public Order getOrder(long id){
    return pedidoRepository.getOrder(id);
  }

}
