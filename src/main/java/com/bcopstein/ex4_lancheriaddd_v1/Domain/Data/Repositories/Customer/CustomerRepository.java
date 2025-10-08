package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

public interface CustomerRepository {
    Customer getCustomerByCpf(String cpf);
    Customer getCustomerByEmail(String email);
    Customer createCustomer(Customer customer);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
