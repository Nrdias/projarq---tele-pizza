package com.bcopstein.ex4_lancheriaddd_v1.Application.Order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Customer.CustomerRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Data.Repositories.Order.OrderRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Customer;
import com.bcopstein.ex4_lancheriaddd_v1.Domain.Entities.Order;

@Component
public class GetCustomerDeliveredOrdersBetweenDatesUseCase {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public GetCustomerDeliveredOrdersBetweenDatesUseCase(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<Order> run(String customerCpf, LocalDateTime startDate, LocalDateTime endDate) throws CustomerNotFoundException {
        Customer customer = customerRepository.getCustomerByCpf(customerCpf);
        if (customer == null) {
            throw new CustomerNotFoundException("Cliente com CPF '" + customerCpf + "' não encontrado");
        }
        
        return orderRepository.getCustomerDeliveredOrdersBetweenDates(customerCpf, startDate, endDate);
    }
}