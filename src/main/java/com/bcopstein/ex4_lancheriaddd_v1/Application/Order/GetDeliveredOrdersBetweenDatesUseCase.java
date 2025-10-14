package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Component
public class GetDeliveredOrdersBetweenDatesUseCase {
    private OrderRepository orderRepository;

    @Autowired
    public GetDeliveredOrdersBetweenDatesUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> run(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getDeliveredOrdersBetweenDates(startDate, endDate);
    }
}