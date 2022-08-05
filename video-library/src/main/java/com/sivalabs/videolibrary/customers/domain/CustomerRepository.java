package com.sivalabs.videolibrary.customers.domain;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    Customer save(Customer user);
}
