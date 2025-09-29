package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

public class OrderItem {
    private Product item;
    private int quantity;

    public OrderItem(Product item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Product getItem() { return item; }
    public int getQuantity() { return quantity; }
}
