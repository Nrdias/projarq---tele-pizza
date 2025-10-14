package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import java.time.LocalDate;
import java.util.List;

public class CustomerDeliveredOrdersBetweenDatesResponse {
    private final String customerCpf;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<Order> orders;
    private final int totalOrders;
    private final boolean success;
    private final String message;

    public CustomerDeliveredOrdersBetweenDatesResponse(String customerCpf, LocalDate startDate, LocalDate endDate, List<Order> orders) {
        this.customerCpf = customerCpf;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orders = orders;
        this.totalOrders = orders.size();
        this.success = true;
        this.message = "Customer orders retrieved successfully";
    }

    public CustomerDeliveredOrdersBetweenDatesResponse(String errorMessage) {
        this.customerCpf = null;
        this.startDate = null;
        this.endDate = null;
        this.orders = null;
        this.totalOrders = 0;
        this.success = false;
        this.message = errorMessage;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}