package com.sivalabs.videolibrary.catalog.domain;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Setter
@Getter
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class CategoryEntity extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "category_id_generator",
            sequenceName = "category_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "category_id_generator")
    private Long id;

    private Long tmdbId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    @ManyToMany(mappedBy = "categories")
    private Set<ProductEntity> products;
}
