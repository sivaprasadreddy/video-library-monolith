package com.sivalabs.videolibrary.customers.domain;

import com.sivalabs.videolibrary.common.exception.ApplicationException;
import com.sivalabs.videolibrary.common.exception.BadRequestException;
import com.sivalabs.videolibrary.common.exception.UserNotFoundException;
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
    public Optional<CustomerEntity> getUserById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerEntity> getUserByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public CustomerEntity createUser(CustomerEntity user) {
        if (customerRepository.existsByEmail(user.getEmail())) {
            throw new ApplicationException("Email " + user.getEmail() + " is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return customerRepository.save(user);
    }

    public CustomerEntity updateUser(CustomerEntity user) {
        Optional<CustomerEntity> userOptional = customerRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
        CustomerEntity existingUser = userOptional.get();
        existingUser.setName(user.getName());
        return customerRepository.save(existingUser);
    }

    public void changePassword(String email, String oldPwd, String newPwd) {
        Optional<CustomerEntity> userByEmail = this.getUserByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        CustomerEntity user = userByEmail.get();
        if (passwordEncoder.matches(oldPwd, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPwd));
            customerRepository.save(user);
        } else {
            throw new BadRequestException("Current password doesn't match");
        }
    }
}
