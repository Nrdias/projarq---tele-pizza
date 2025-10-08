package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.customer;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.RegisterCustomerResponse;

public class RegisterCustomerPresenter {
    private boolean success;
    private String message;
    private CustomerPresenter customer;

    public RegisterCustomerPresenter() {}

    public RegisterCustomerPresenter(RegisterCustomerResponse response) {
        this.success = response.isSuccess();
        this.message = response.getMessage();
        
        if (response.getCustomer() != null) {
            this.customer = new CustomerPresenter(
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

    public CustomerPresenter getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerPresenter customer) {
        this.customer = customer;
    }

    public static class CustomerPresenter {
        private String cpf;
        private String name;
        private String phone;
        private String address;
        private String email;

        public CustomerPresenter() {}

        public CustomerPresenter(String cpf, String name, String phone, String address, String email) {
            this.cpf = cpf;
            this.name = name;
            this.phone = phone;
            this.address = address;
            this.email = email;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}