package com.sivalabs.videolibrary.customers.service;

import static com.sivalabs.videolibrary.common.utils.Constants.ROLE_USER;

import com.sivalabs.videolibrary.common.exception.ApplicationException;
import com.sivalabs.videolibrary.common.exception.BadRequestException;
import com.sivalabs.videolibrary.common.exception.UserNotFoundException;
import com.sivalabs.videolibrary.customers.entity.Role;
import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.customers.repository.RoleRepository;
import com.sivalabs.videolibrary.customers.repository.UserRepository;
import java.util.Collections;
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
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ApplicationException("Email " + user.getEmail() + " is already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> roleUser = roleRepository.findByName(ROLE_USER);
        user.setRoles(Collections.singletonList(roleUser.orElse(null)));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with id " + user.getId() + " not found");
        }
        User existingUser = userOptional.get();
        existingUser.setName(user.getName());
        return userRepository.save(existingUser);
    }

    public void changePassword(String email, String oldPwd, String newPwd) {
        Optional<User> userByEmail = this.getUserByEmail(email);
        if (userByEmail.isEmpty()) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        User user = userByEmail.get();
        if (passwordEncoder.matches(oldPwd, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPwd));
            userRepository.save(user);
        } else {
            throw new BadRequestException("Current password doesn't match");
        }
    }
}