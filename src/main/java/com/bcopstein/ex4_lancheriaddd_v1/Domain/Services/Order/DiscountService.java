package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

public interface DiscountService {
    double calculateDiscount(String customerCpf, double subtotal);
}

