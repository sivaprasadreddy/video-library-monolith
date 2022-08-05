package com.sivalabs.videolibrary.customers.web;

import com.sivalabs.videolibrary.common.exception.ResourceAlreadyExistsException;
import com.sivalabs.videolibrary.customers.domain.Customer;
import com.sivalabs.videolibrary.customers.domain.CustomerRegistrationRequest;
import com.sivalabs.videolibrary.customers.domain.CustomerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("customer", new CustomerRegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerCustomer(
            @Valid @ModelAttribute("customer")
                    CustomerRegistrationRequest customerRegistrationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            Customer customer = new Customer();
            customer.setName(customerRegistrationRequest.getName());
            customer.setEmail(customerRegistrationRequest.getEmail());
            customer.setPassword(customerRegistrationRequest.getPassword());
            customerService.createUser(customer);
            redirectAttributes.addFlashAttribute("msg", "Registration is successful");
        } catch (ResourceAlreadyExistsException e) {
            log.error("Registration err", e);
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}
