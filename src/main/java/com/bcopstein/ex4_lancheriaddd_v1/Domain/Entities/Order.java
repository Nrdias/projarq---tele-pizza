package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public enum Status {
        NOVO,
        APROVADO,
        PAGO,
        AGUARDANDO,
        PREPARACAO,
        PRONTO,
        TRANSPORTE,
        ENTREGUE,
        CANCELADO
    }
    private long id;
    private Customer customer;
    private LocalDateTime paymentDateTime;
    private List<OrderItem> items;
    private Status status;
    private double value;
    private double taxes;
    private double discount;
    private double chargedValue;

    public Order(long id, Customer customer, LocalDateTime paymentDateTime, List<OrderItem> items,
            Order.Status status, double value, double taxes, double discount, double chargedValue) {
        this.id = id;
        this.customer = customer;
        this.paymentDateTime = paymentDateTime;
        this.items = items;
        this.status = status;
        this.value = value;
        this.taxes = taxes;
        this.discount = discount;
        this.chargedValue = chargedValue;
    }

    public long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public double getValue() {
        return value;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getDiscount() {
        return discount;
    }

    public double getChargedValue() {
        return chargedValue;
    }
}
