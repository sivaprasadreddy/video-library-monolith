package com.sivalabs.videolibrary.customers.domain;

import com.sivalabs.videolibrary.common.exception.ApplicationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<Customer> getUserByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer createUser(Customer user) {
        if (customerRepository.existsByEmail(user.getEmail())) {
            throw new ApplicationException("Email " + user.getEmail() + " is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return customerRepository.save(user);
    }
}
