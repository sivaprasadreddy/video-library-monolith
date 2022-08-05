package com.sivalabs.videolibrary.config.security;

import com.sivalabs.videolibrary.customers.domain.Customer;
import com.sivalabs.videolibrary.customers.domain.CustomerService;
import com.sivalabs.videolibrary.customers.domain.SecurityUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Customer> customer = customerService.getUserByEmail(username);
        if (customer.isPresent()) {
            return new SecurityUser(customer.get());
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }
}
