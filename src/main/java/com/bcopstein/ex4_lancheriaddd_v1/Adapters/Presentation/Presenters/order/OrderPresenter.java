package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order;

import java.time.LocalDateTime;
import java.util.List;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order.Status;


public class OrderPresenter {
    private long id;
    private Customer customer;
    private LocalDateTime paymentDateTime;
    private List<OrderItemPresenter> items;
    private Order.Status status;
    private double value;
    private double taxes;
    private double discount;
    private double chargedValue;

    public OrderPresenter() {}

    public OrderPresenter(long id, Customer customer, LocalDateTime paymentDateTime, List<OrderItemPresenter> items,
            Status status, double value, double taxes, double discount, double chargedValue) {
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

    public void setId(long id) {
        this.id = id;
    }

    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public List<OrderItemPresenter> getItens() {
        return items;
    }

    public void setItens(List<OrderItemPresenter> itens) {
        this.items = itens;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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

    public static class OrderItemPresenter {
        private long productId;
        private String productDescription;
        private int productPrice;
        private int quantity;

        public OrderItemPresenter() {}

        public OrderItemPresenter(long productId, String productDescription, int productPrice, int quantity) {
            this.productId = productId;
            this.productDescription = productDescription;
            this.productPrice = productPrice;
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public String getProductDescription() {
            return productDescription;
        }

        public void setProductDescription(String productDescription) {
            this.productDescription = productDescription;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
                this.productPrice = productPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
