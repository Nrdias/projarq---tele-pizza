package com.bcopstein.ex4_lancheriaddd_v1.Application.Responses;

public class AuthenticateCustomerRequest {
    private String email;
    private String password;

    public AuthenticateCustomerRequest() {}

    public AuthenticateCustomerRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}