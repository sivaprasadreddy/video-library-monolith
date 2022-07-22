package com.sivalabs.videolibrary.orders.domain;

import java.math.BigDecimal;
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
public class OrderedProductEntity {
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

    private BigDecimal price;
}
