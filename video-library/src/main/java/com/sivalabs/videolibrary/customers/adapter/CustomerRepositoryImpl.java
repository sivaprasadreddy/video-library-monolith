package com.sivalabs.videolibrary.customers.adapter;

import com.sivalabs.videolibrary.customers.domain.Customer;
import com.sivalabs.videolibrary.customers.domain.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {
    private final JpaCustomerRepository jpaCustomerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<Customer> findByEmail(String email) {
        return jpaCustomerRepository.findByEmail(email).map(customerMapper::map);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaCustomerRepository.existsByEmail(email);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = customerMapper.mapToEntity(customer);
        var savedCustomer = jpaCustomerRepository.save(entity);
        return customerMapper.map(savedCustomer);
    }
}
