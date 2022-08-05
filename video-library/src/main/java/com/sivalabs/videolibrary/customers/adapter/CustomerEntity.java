package com.sivalabs.videolibrary.customers.adapter;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class CustomerEntity extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "customer_id_generator",
            sequenceName = "customer_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "customer_id_generator")
    private Long id;

    @Column(nullable = false)
    @NotBlank()
    private String name;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 4)
    private String password;
}
