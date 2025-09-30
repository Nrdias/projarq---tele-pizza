package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.order;

import java.util.List;
import java.util.stream.Collectors;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.PaginatedOrdersResponse;

public class PaginatedOrdersPresenter {
    private List<OrderPresenter> orders;
    private int currentPage;
    private int pageSize;
    private long totalOrders;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginatedOrdersPresenter() {}

    public PaginatedOrdersPresenter(PaginatedOrdersResponse response) {
        this.orders = response.getOrders().stream()
            .map(order -> {
                List<OrderPresenter.OrderItemPresenter> itensPresenter = order.getItems().stream()
                    .map(item -> new OrderPresenter.OrderItemPresenter(
                        item.getItem().getId(),
                        item.getItem().getDescription(),
                        item.getItem().getPrice(),
                        item.getQuantity()
                    ))
                    .collect(Collectors.toList());
                
                return new OrderPresenter(
                    order.getId(),
                    order.getCustomer(),
                    order.getPaymentDateTime(),
                    itensPresenter,
                    order.getStatus(),
                    order.getValue(),
                    order.getTaxes(),
                    order.getDiscount(),
                    order.getChargedValue()
                );
            })
            .collect(Collectors.toList());
        
        this.currentPage = response.getCurrentPage();
        this.pageSize = response.getPageSize();
        this.totalOrders = response.getTotalOrders();
        this.totalPages = response.getTotalPages();
        this.hasNext = response.isHasNext();
        this.hasPrevious = response.isHasPrevious();
    }

    public List<OrderPresenter> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderPresenter> orders) {
        this.orders = orders;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}