package com.sivalabs.videolibrary.catalog.mappers;

import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.catalog.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {
    public static final String TMDB_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";

    public ProductDTO map(Product product) {
        if (product == null) {
            return null;
        }
        return ProductDTO.builder()
                .id(product.getId())
                .budget(product.getBudget())
                .homepage(product.getHomepage())
                .tmdbId(product.getTmdbId())
                .originalLanguage(product.getOriginalLanguage())
                .overview(product.getOverview())
                .posterPath(TMDB_IMAGE_PATH_PREFIX + product.getPosterPath())
                .releaseDate(product.getReleaseDate())
                .revenue(product.getRevenue())
                .tagline(product.getTagline())
                .runtime(product.getRuntime())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(product.getCategories())
                .build();
    }
}
