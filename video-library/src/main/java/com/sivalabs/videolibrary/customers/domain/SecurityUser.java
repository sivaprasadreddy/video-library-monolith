package com.sivalabs.videolibrary.customers.domain;

import java.util.List;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@EqualsAndHashCode(exclude = "user", callSuper = true)
public class SecurityUser extends User {

    private final CustomerEntity user;

    public SecurityUser(CustomerEntity user) {
        super(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }

    public CustomerEntity getUser() {
        return user;
    }
}
