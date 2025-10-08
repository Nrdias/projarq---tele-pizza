package com.bcopstein.ex4_lancheriaddd_v1.Adapters.Dados.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;

@Component
public class CustomerRepositoryJDBC implements CustomerRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer getCustomerByCpf(String cpf) {
        String sql = "SELECT cpf, nome, celular, endereco, email FROM clientes WHERE cpf = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String customerCpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");
                
                return new Customer(customerCpf, nome, celular, endereco, email);
            }, cpf);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // Customer not found, return null
            return null;
        }
    }
}
