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
        String sql = "SELECT cpf, nome, celular, endereco, email, senha FROM clientes WHERE cpf = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String customerCpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String endereco = rs.getString("endereco");
                String email = rs.getString("email");
                String senha = rs.getString("senha");
                
                return new Customer(customerCpf, nome, celular, endereco, email, senha);
            }, cpf);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT cpf, nome, celular, endereco, email, senha FROM clientes WHERE email = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                String customerCpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                String celular = rs.getString("celular");
                String endereco = rs.getString("endereco");
                String customerEmail = rs.getString("email");
                String senha = rs.getString("senha");
                
                return new Customer(customerCpf, nome, celular, endereco, customerEmail, senha);
            }, email);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Customer createCustomer(Customer customer) {
        String sql = "INSERT INTO clientes (cpf, nome, celular, endereco, email, senha) VALUES (?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            customer.getCpf(),
            customer.getName(),
            customer.getPhone(),
            customer.getAddress(),
            customer.getEmail(),
            customer.getPassword()
        );
        
        return customer;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsByCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cpf);
        return count != null && count > 0;
    }
}
