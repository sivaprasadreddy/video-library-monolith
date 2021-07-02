package com.sivalabs.videolibrary.catalog.web.mappers;

import static com.sivalabs.videolibrary.common.utils.Constants.TMDB_IMAGE_PATH_PREFIX;

import com.sivalabs.videolibrary.catalog.entity.Product;
import com.sivalabs.videolibrary.catalog.web.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOMapper {

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
