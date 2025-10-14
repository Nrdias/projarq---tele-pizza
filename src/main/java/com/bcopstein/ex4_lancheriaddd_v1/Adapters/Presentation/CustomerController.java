package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RegisterCustomerPresenter> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        RegisterCustomerResponse response = registerCustomerUseCase.run(request);
        RegisterCustomerPresenter presenter = new RegisterCustomerPresenter(response);
        
        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(presenter);
        } else {
            String message = response.getMessage().toLowerCase();
            HttpStatus status;
            
            if (message.contains("já cadastrado") || message.contains("already exists")) {
                status = HttpStatus.CONFLICT; 
            } else if (message.contains("obrigatório") || message.contains("inválido") || 
                      message.contains("required") || message.contains("invalid")) {
                status = HttpStatus.BAD_REQUEST; 
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR; 
            }
            
            return ResponseEntity.status(status).body(presenter);
        }
    }

    @PostMapping("/authenticate")
    @CrossOrigin("*")
    public ResponseEntity<AuthenticateCustomerPresenter> authenticateCustomer(@RequestBody AuthenticateCustomerRequest request) {
        AuthenticateCustomerResponse response = authenticateCustomerUseCase.run(request);
        AuthenticateCustomerPresenter presenter = new AuthenticateCustomerPresenter(response);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(presenter);
        } else {
            String message = response.getMessage().toLowerCase();
            HttpStatus status;
            
            if (message.contains("credenciais inválidas") || message.contains("invalid credentials")) {
                status = HttpStatus.UNAUTHORIZED; 
            } else if (message.contains("obrigatório") || message.contains("required")) {
                status = HttpStatus.BAD_REQUEST; 
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR; 
            }
            
            return ResponseEntity.status(status).body(presenter);
        }
    }
}