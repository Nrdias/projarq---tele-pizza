package com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

public interface CustomerRepository {
    Customer getCustomerByCpf(String cpf);
}
