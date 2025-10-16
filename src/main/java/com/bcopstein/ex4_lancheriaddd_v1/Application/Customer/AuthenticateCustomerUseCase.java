package com.bcopstein.ex4_lancheriaddd_v1.Application.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Services.Security.JwtService;

@Component
public class AuthenticateCustomerUseCase {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;

    @Autowired
    public AuthenticateCustomerUseCase(CustomerRepository customerRepository, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    public AuthenticateCustomerResponse run(AuthenticateCustomerRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return new AuthenticateCustomerResponse(false, "Email é obrigatório", null, null);
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                return new AuthenticateCustomerResponse(false, "Senha é obrigatória", null, null);
            }

            Customer customer = customerRepository.getCustomerByEmail(request.getEmail());

            if (customer == null) {
                return new AuthenticateCustomerResponse(false, "Credenciais inválidas", null, null);
            }

            if (!customer.getPassword().equals(request.getPassword())) {
                return new AuthenticateCustomerResponse(false, "Credenciais inválidas", null, null);
            }
            
            Customer authenticatedCustomer = new Customer(
                customer.getCpf(),
                customer.getName(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getEmail()
            );

            String token = jwtService.generateToken(authenticatedCustomer.getEmail());
            return new AuthenticateCustomerResponse(true, "Autenticação realizada com sucesso", authenticatedCustomer, token);

        } catch (Exception e) {
            return new AuthenticateCustomerResponse(false, "Erro interno: " + e.getMessage(), null, null);
        }
    }
}