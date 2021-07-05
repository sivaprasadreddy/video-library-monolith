package com.sivalabs.videolibrary.customers.web.controller;

import com.sivalabs.videolibrary.common.exception.ApplicationException;
import com.sivalabs.videolibrary.common.exception.ResourceAlreadyExistsException;
import com.sivalabs.videolibrary.customers.entity.User;
import com.sivalabs.videolibrary.customers.model.ChangePasswordRequest;
import com.sivalabs.videolibrary.customers.model.CreateUserRequest;
import com.sivalabs.videolibrary.customers.model.UpdateUserRequest;
import com.sivalabs.videolibrary.customers.service.SecurityService;
import com.sivalabs.videolibrary.customers.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new CreateUserRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("user") CreateUserRequest createUserRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            User user = new User();
            user.setName(createUserRequest.getName());
            user.setEmail(createUserRequest.getEmail());
            user.setPassword(createUserRequest.getPassword());
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("msg", "Registration is successful");
        } catch (ResourceAlreadyExistsException e) {
            log.error("Registration err", e);
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String viewProfile(Model model) {
        Long loginUserId = securityService.getLoginUserId();
        var user = userService.getUserById(loginUserId).orElseThrow();
        var updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setName(user.getName());
        updateUserRequest.setEmail(user.getEmail());
        model.addAttribute("user", updateUserRequest);
        return "profile";
    }

    @PostMapping("/update-profile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(
            @Valid @ModelAttribute("user") UpdateUserRequest updateUserRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
        try {
            Long loginUserId = securityService.getLoginUserId();
            var user = userService.getUserById(loginUserId).orElseThrow();
            user.setName(updateUserRequest.getName());
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("msg", "User updated successfully");
        } catch (ApplicationException e) {
            log.error("Update User err", e);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePasswordForm(Model model) {
        model.addAttribute("changePassword", new ChangePasswordRequest());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            @Valid @ModelAttribute("changePassword") ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "change-password";
        }
        try {
            var loginUser = securityService.loginUser();
            userService.changePassword(
                    loginUser.getEmail(),
                    changePasswordRequest.getOldPassword(),
                    changePasswordRequest.getNewPassword());
            redirectAttributes.addFlashAttribute("msg", "Password changed successfully");
        } catch (ApplicationException e) {
            log.error("ChangePassword err", e);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }
        return "redirect:/change-password";
    }
}
