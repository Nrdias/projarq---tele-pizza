package com.bcopstein.ex4_lancheriaddd_v1.Application.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

@Component
public class AuthenticateCustomerUseCase {
    private final CustomerRepository customerRepository;

    @Autowired
    public AuthenticateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public AuthenticateCustomerResponse run(AuthenticateCustomerRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return new AuthenticateCustomerResponse(false, "Email é obrigatório", null);
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return new AuthenticateCustomerResponse(false, "Senha é obrigatória", null);
            }

            Customer customer = customerRepository.getCustomerByEmail(request.getEmail());

            if (customer == null) {
                return new AuthenticateCustomerResponse(false, "Credenciais inválidas", null);
            }

            if (!customer.getPassword().equals(request.getPassword())) {
                return new AuthenticateCustomerResponse(false, "Credenciais inválidas", null);
            }
            
            Customer authenticatedCustomer = new Customer(
                customer.getCpf(),
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getEmail()
            );

            return new AuthenticateCustomerResponse(true, "Autenticação realizada com sucesso", authenticatedCustomer);

        } catch (Exception e) {
            return new AuthenticateCustomerResponse(false, "Erro interno: " + e.getMessage(), null);
        }
    }
}