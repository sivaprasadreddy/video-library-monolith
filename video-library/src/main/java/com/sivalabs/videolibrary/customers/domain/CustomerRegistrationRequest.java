package com.sivalabs.videolibrary.customers.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
