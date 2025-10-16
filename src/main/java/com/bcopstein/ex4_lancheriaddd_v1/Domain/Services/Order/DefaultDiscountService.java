package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import java.time.LocalDateTime;

@Service
public class DefaultDiscountService implements DiscountService {
    private static final double DISCOUNT_RATE = 0.07; // 7%
    private static final int MIN_ORDERS = 4;
    private static final int DAYS = 20;

    private final OrderRepository orderRepository;

    @Autowired
    public DefaultDiscountService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public double calculateDiscount(String customerCpf, double subtotal) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.minusDays(DAYS);
        int orderCount = orderRepository.countDeliveredOrdersByCustomerBetweenDates(customerCpf, startDate, now);
        if (orderCount >= MIN_ORDERS) {
            return subtotal * DISCOUNT_RATE;
        }
        return 0.0;
    }
}
