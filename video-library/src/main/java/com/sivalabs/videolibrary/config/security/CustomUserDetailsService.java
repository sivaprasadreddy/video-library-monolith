package com.sivalabs.videolibrary.config.security;

import com.sivalabs.videolibrary.core.entity.User;
import com.sivalabs.videolibrary.core.model.SecurityUser;
import com.sivalabs.videolibrary.core.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userService.getUserByEmail(username);
        if (user.isPresent()) {
            return new SecurityUser(user.get());
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }
}
