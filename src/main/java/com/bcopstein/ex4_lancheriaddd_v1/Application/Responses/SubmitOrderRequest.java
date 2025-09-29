package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import java.util.List;

public class SubmitOrderRequest {
    private String customerCpf;
    private List<OrderItemRequest> items;

    public SubmitOrderRequest() {}

    public SubmitOrderRequest(String customerCpf, List<OrderItemRequest> items) {
        this.customerCpf = customerCpf;
        this.items = items;
    }

    public String getCustomerCpf() {
        return customerCpf;
    }

    public void setCustomerCpf(String customerCpf) {
        this.customerCpf = customerCpf;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public static class OrderItemRequest {
        private long productId;
        private int quantity;

        public OrderItemRequest() {}

        public OrderItemRequest(long productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public long getProductId() {
            return productId;
        }

        public void setProductId(long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
