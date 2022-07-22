package com.sivalabs.videolibrary.catalog.domain;

import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {
    public static final String TMDB_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";

    public ProductDTO map(ProductEntity product) {
        if (product == null) {
            return null;
        }
        return ProductDTO.builder()
                .id(product.getId())
                .tmdbId(product.getTmdbId())
                .originalLanguage(product.getOriginalLanguage())
                .overview(product.getOverview())
                .posterPath(TMDB_IMAGE_PATH_PREFIX + product.getPosterPath())
                .releaseDate(product.getReleaseDate())
                .tagline(product.getTagline())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
    }
}
