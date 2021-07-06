package com.sivalabs.videolibrary.customers.service;

import static com.sivalabs.videolibrary.common.utils.Constants.ROLE_ADMIN;

import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.customers.model.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Loggable
public class SecurityService {

    private final UserService userService;

    public User loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            return securityUser.getUser();
        } /*else if (authentication instanceof UsernamePasswordAuthenticationToken) {
              UserDetails userDetails = (UserDetails) authentication.getPrincipal();
              return userService.getUserByEmail(userDetails.getUsername()).orElse(null);
          }*/
        return null;
    }

    public Long getLoginUserId() {
        User user = loginUser();
        return user == null ? null : user.getId();
    }

    public boolean isCurrentUserAdmin() {
        User loginUser = loginUser();
        if (loginUser != null) {
            return loginUser.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(ROLE_ADMIN));
        }
        return false;
    }

    public boolean isCurrentUserHasPrivilege(Long userId) {
        return loginUser() != null && (userId.equals(loginUser().getId()) || isCurrentUserAdmin());
    }
}
