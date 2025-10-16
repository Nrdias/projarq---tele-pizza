package com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Order;

import org.springframework.stereotype.Service;

@Service
public class DefaultTaxService implements TaxService {
    private static final double TAX_RATE = 0.10; // 10%

    @Override
    public double calculateTax(double subtotal) {
        return subtotal * TAX_RATE;
    }
}
