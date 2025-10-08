package com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities;

public class Customer {
    private String cpf;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;

    public Customer(String cpf, String name, String phone, String address, String email) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Customer(String cpf, String name, String phone, String address, String email, String password) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public String getCpf() { return cpf; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
