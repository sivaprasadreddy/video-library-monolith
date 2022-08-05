package com.sivalabs.videolibrary.customers.domain;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

    private final Customer customer;

    public SecurityUser(Customer customer) {
        super(
                customer.getEmail(),
                customer.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
