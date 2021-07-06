package com.sivalabs.videolibrary.customers.model;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    private String email;
}
