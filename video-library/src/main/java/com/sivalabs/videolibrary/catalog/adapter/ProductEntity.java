package com.sivalabs.videolibrary.catalog.adapter;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class ProductEntity extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "product_id_generator",
            sequenceName = "product_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "product_id_generator")
    private Long id;

    private String title;

    @Column(name = "uuid")
    private Long uuid;

    @Column(nullable = false)
    private BigDecimal price;

    private String overview;

    private String tagline;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "original_language")
    private String originalLanguage;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")})
    private Set<CategoryEntity> categories = new HashSet<>();
}
