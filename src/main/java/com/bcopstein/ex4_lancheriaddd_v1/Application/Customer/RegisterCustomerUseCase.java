package com.bcopstein.ex4_lancheriaddd_v1.Application.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.RegisterCustomerRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.RegisterCustomerResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

@Component
public class RegisterCustomerUseCase {
    private final CustomerRepository customerRepository;

    @Autowired
    public RegisterCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public RegisterCustomerResponse run(RegisterCustomerRequest request) {
        try {
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "Nome é obrigatório", null);
            }

            if (request.getCpf() == null || request.getCpf().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "CPF é obrigatório", null);
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "Email é obrigatório", null);
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "Senha é obrigatória", null);
            }

            if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "Celular é obrigatório", null);
            }

            if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
                return new RegisterCustomerResponse(false, "Endereço é obrigatório", null);
            }

            if (!isValidEmail(request.getEmail())) {
                return new RegisterCustomerResponse(false, "Formato de email inválido", null);
            }

            if (customerRepository.existsByEmail(request.getEmail())) {
                return new RegisterCustomerResponse(false, "Email já cadastrado no sistema", null);
            }

            if (customerRepository.existsByCpf(request.getCpf())) {
                return new RegisterCustomerResponse(false, "CPF já cadastrado no sistema", null);
            }

            Customer newCustomer = new Customer(
                request.getCpf(),
                request.getName(),
                request.getPhone(),
                request.getAddress(),
                request.getEmail(),
                request.getPassword()
            );

            Customer savedCustomer = customerRepository.createCustomer(newCustomer);

            return new RegisterCustomerResponse(true, "Cliente registrado com sucesso", savedCustomer);

        } catch (Exception e) {
            return new RegisterCustomerResponse(false, "Erro interno: " + e.getMessage(), null);
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}