package com.sivalabs.videolibrary.customers.domain;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    private Long id;
    @NotEmpty private String name;

    @NotEmpty
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty
    @Size(min = 4)
    private String password;
}
