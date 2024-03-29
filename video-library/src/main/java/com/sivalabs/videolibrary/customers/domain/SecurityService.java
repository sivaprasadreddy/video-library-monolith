package com.sivalabs.videolibrary.customers.domain;

import com.sivalabs.videolibrary.common.logging.Loggable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Loggable
public class SecurityService {

    public Customer loginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof SecurityUser securityUser) {
            return securityUser.getCustomer();
        }
        return null;
    }

    public Long getLoginUserId() {
        Customer user = loginUser();
        return user == null ? null : user.getId();
    }
}
