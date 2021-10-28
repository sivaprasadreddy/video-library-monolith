package com.sivalabs.videolibrary.catalog.entity;

import com.sivalabs.videolibrary.common.entity.BaseEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@EqualsAndHashCode(
        of = {"id"},
        callSuper = false)
public class Product extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "product_id_generator",
            sequenceName = "product_id_seq",
            allocationSize = 1)
    @GeneratedValue(generator = "product_id_generator")
    private Long id;

    private String title;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "imdb_id")
    private String imdbId;

    private String overview;

    private String tagline;

    private Integer runtime;

    private Double revenue;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "poster_path")
    private String posterPath;

    private Double budget;

    private String homepage;

    @Column(name = "original_language")
    private String originalLanguage;

    @Column(name = "vote_average")
    private Double voteAverage;

    @Column(name = "vote_count")
    private Long voteCount;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_category",
            joinColumns = {@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")})
    private Set<Category> categories = new HashSet<>();
}
