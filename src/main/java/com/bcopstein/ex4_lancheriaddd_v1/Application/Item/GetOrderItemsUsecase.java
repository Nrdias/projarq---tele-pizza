package com.bcopstein.ex4_lancheriaddd_v1.Application.Item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.OrderItem;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order.OrderService;

@Component
public class GetOrderItemsUsecase {
    private OrderService orderService;

    @Autowired
    public GetOrderItemsUsecase(OrderService orderService){
        this.orderService = orderService;
    }

    public List<OrderItem> run(long id){
        return orderService.getOrderItems(id);
    }
}
