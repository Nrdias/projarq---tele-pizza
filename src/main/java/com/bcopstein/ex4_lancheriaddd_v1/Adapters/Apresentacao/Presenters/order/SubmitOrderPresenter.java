package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Apresentacao.Presenters.order;

import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.SubmitOrderResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public class SubmitOrderPresenter {
    private boolean approved;
    private String message;
    private Long orderId;
    private String customerCpf;
    private List<OrderPresenter.OrderItemPresenter> items;
    private String status;
    private double totalValue;
    private double taxes;
    private double discount;
    private double chargedValue;

    public SubmitOrderPresenter() {}

    public SubmitOrderPresenter(SubmitOrderResponse response) {
        this.approved = response.isApproved();
        this.message = response.getMessage();
        
        if (response.getOrder() != null) {
            Order order = response.getOrder();
            this.orderId = order.getId();
            this.customerCpf = order.getCustomer().getCpf();
            this.status = order.getStatus().toString();
            this.totalValue = response.getTotalValue();
            this.taxes = response.getTaxes();
            this.discount = response.getDiscount();
            this.chargedValue = response.getChargedValue();
            
            this.items = order.getItems().stream()
                .map(item -> new OrderPresenter.OrderItemPresenter(
                    item.getItem().getId(),
                    item.getItem().getDescription(),
                    item.getItem().getPrice(),
                    item.getQuantity()
                ))
                .collect(Collectors.toList());
        }
    }

    // Getters and setters
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public void setCustomerCpf(String customerCpf) {
        this.customerCpf = customerCpf;
    }

    public List<OrderPresenter.OrderItemPresenter> getItems() {
        return items;
    }

    public void setItems(List<OrderPresenter.OrderItemPresenter> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
