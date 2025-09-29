package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order.Status;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order.OrderService;

@Component
public class GetOrderStatusUsecase {

  private OrderService orderService;

  @Autowired
  public GetOrderStatusUsecase(OrderService orderService) {
    this.orderService = orderService;
  }

  public Status run(long id){
    Order result = orderService.getOrder(id);

    return result.getStatus();
  }
}
