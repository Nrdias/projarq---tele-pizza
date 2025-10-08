package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.customer.AuthenticateCustomerPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation.Presenters.customer.RegisterCustomerPresenter;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Customer.AuthenticateCustomerUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Customer.RegisterCustomerUseCase;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.AuthenticateCustomerResponse;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.RegisterCustomerRequest;
import com.bcopstein.ex4_lancheriaddd_v1.Application.Responses.RegisterCustomerResponse;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    private final RegisterCustomerUseCase registerCustomerUseCase;
    private final AuthenticateCustomerUseCase authenticateCustomerUseCase;

    @Autowired
    public CustomerController(RegisterCustomerUseCase registerCustomerUseCase,
                            AuthenticateCustomerUseCase authenticateCustomerUseCase) {
        this.registerCustomerUseCase = registerCustomerUseCase;
        this.authenticateCustomerUseCase = authenticateCustomerUseCase;
    }

    @PostMapping("/register")
    @CrossOrigin("*")
    public RegisterCustomerPresenter registerCustomer(@RequestBody RegisterCustomerRequest request) {
        RegisterCustomerResponse response = registerCustomerUseCase.run(request);
        return new RegisterCustomerPresenter(response);
    }

    @PostMapping("/authenticate")
    @CrossOrigin("*")
    public AuthenticateCustomerPresenter authenticateCustomer(@RequestBody AuthenticateCustomerRequest request) {
        AuthenticateCustomerResponse response = authenticateCustomerUseCase.run(request);
        return new AuthenticateCustomerPresenter(response);
    }
}