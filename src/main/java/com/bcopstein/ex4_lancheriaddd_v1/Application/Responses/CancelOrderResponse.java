package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

public class CancelOrderResponse {
    private boolean success;
    private String message;
    private long orderId;

    public CancelOrderResponse() {}

    public CancelOrderResponse(boolean success, String message, long orderId) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}