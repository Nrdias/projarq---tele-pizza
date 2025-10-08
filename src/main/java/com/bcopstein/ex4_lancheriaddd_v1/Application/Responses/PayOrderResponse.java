package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public class PayOrderResponse {
    private final boolean success;
    private final String message;
    private final long orderId;
    private final Order.Status orderStatus;

    public PayOrderResponse(boolean success, String message, long orderId, Order.Status orderStatus) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public long getOrderId() {
        return orderId;
    }

    public Order.Status getOrderStatus() {
        return orderStatus;
    }
}