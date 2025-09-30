package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import java.util.List;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

public class PaginatedOrdersResponse {
    private List<Order> orders;
    private int currentPage;
    private int pageSize;
    private long totalOrders;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PaginatedOrdersResponse() {}

    public PaginatedOrdersResponse(List<Order> orders, int currentPage, int pageSize, long totalOrders) {
        this.orders = orders;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalOrders = totalOrders;
        this.totalPages = (int) Math.ceil((double) totalOrders / pageSize);
        this.hasNext = currentPage < totalPages - 1;
        this.hasPrevious = currentPage > 0;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
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