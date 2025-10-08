package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.customer;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerResponse;

public class AuthenticateCustomerPresenter {
    private boolean success;
    private String message;
    private RegisterCustomerPresenter.CustomerPresenter customer;

    public AuthenticateCustomerPresenter() {}

    public AuthenticateCustomerPresenter(AuthenticateCustomerResponse response) {
        this.success = response.isSuccess();
        this.message = response.getMessage();
        
        if (response.getCustomer() != null) {
            this.customer = new RegisterCustomerPresenter.CustomerPresenter(
                response.getCustomer().getCpf(),
                response.getCustomer().getName(),
                response.getCustomer().getPhone(),
                response.getCustomer().getAddress(),
                response.getCustomer().getEmail()
            );
        }
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
}