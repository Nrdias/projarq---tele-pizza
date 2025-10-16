package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

public class AuthenticateCustomerResponse {
    private boolean success;
    private String message;
    private Customer customer;
    private final String token;

    public AuthenticateCustomerResponse() {
        this.token = "";
    }

    public AuthenticateCustomerResponse(boolean success, String message, Customer customer, String token) {
        this.success = success;
        this.message = message;
        this.customer = customer;
        this.token = token;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }
}