package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public class SubmitOrderResponse {
    private boolean approved;
    private String message;
    private Order order;
    private double totalValue;
    private double taxes;
    private double discount;
    private double chargedValue;

    public SubmitOrderResponse() {}

    public SubmitOrderResponse(boolean approved, String message, Order order, 
                             double totalValue, double taxes, double discount, double chargedValue) {
        this.approved = approved;
        this.message = message;
        this.order = order;
        this.totalValue = totalValue;
        this.taxes = taxes;
        this.discount = discount;
        this.chargedValue = chargedValue;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getChargedValue() {
        return chargedValue;
    }

    public void setChargedValue(double chargedValue) {
        this.chargedValue = chargedValue;
    }
}
