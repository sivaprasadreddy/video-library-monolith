package com.sivalabs.videolibrary.customers.model;

import com.sivalabs.videolibrary.customers.entity.User;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@EqualsAndHashCode(exclude = "user", callSuper = true)
public class SecurityUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public SecurityUser(User user) {
        super(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
