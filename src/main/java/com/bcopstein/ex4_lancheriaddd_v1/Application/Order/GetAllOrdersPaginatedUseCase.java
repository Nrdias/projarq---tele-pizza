package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.PaginatedOrdersResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Component
public class GetAllOrdersPaginatedUseCase {
    private OrderRepository orderRepository;

    @Autowired
    public GetAllOrdersPaginatedUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public PaginatedOrdersResponse run(int page, int size) {
        // Validate parameters
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 50) { // Limit max size to 50 to prevent performance issues
            size = 5; // Default to 5 as requested
        }

        List<Order> orders = orderRepository.getAllOrdersPaginated(page, size);
        long totalOrders = orderRepository.getTotalOrdersCount();

        return new PaginatedOrdersResponse(orders, page, size, totalOrders);
    }
}