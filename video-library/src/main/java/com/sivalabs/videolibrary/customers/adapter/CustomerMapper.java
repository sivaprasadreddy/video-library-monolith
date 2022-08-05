package com.sivalabs.videolibrary.customers.adapter;

import com.sivalabs.videolibrary.customers.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer map(CustomerEntity entity) {
        return new Customer(
                entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword());
    }

    public CustomerEntity mapToEntity(Customer customer) {
        return new CustomerEntity(
                customer.getId(), customer.getName(), customer.getEmail(), customer.getPassword());
    }
}
