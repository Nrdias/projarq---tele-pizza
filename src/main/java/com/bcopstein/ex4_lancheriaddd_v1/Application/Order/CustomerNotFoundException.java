package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}