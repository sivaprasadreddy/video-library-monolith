package com.sivalabs.videolibrary.customers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sivalabs.videolibrary.common.entity.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class Role extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "role_id_generator")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
