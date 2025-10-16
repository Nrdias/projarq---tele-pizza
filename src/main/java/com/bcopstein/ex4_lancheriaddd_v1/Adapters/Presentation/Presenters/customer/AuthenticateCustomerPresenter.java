package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.customer;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

public class AuthenticateCustomerPresenter {
    private boolean success;
    private String message;
    private RegisterCustomerPresenter.CustomerPresenter customer;
    private final String token;

    public AuthenticateCustomerPresenter() {
        this.token = "";
    }

    public AuthenticateCustomerPresenter(AuthenticateCustomerResponse response) {
        this.success = response.isSuccess();
        this.message = response.getMessage();
        Customer c = response.getCustomer();
        if (c != null) {
            this.customer = new RegisterCustomerPresenter.CustomerPresenter(
                c.getCpf(), c.getName(), c.getPhone(), c.getAddress(), c.getEmail()
            );
        } else {
            this.customer = null;
        }
        this.token = response.getToken();
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

    public RegisterCustomerPresenter.CustomerPresenter getCustomer() {
        return customer;
    }

    public void setCustomer(RegisterCustomerPresenter.CustomerPresenter customer) {
        this.customer = customer;
    }

    public String getToken() {
        return token;
    }
}