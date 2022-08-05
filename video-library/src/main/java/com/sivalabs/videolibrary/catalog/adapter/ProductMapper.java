package com.sivalabs.videolibrary.catalog.adapter;

import com.sivalabs.videolibrary.catalog.domain.Category;
import com.sivalabs.videolibrary.catalog.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public Product map(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        Set<Category> categories = null;
        if (entity.getCategories() != null) {
            categories = entity.getCategories().stream()
                    .map(categoryMapper::map)
                    .collect(toSet());
        }
        return Product.builder()
                .id(entity.getId())
                .uuid(entity.getUuid())
                .originalLanguage(entity.getOriginalLanguage())
                .overview(entity.getOverview())
                .posterPath(entity.getPosterPath())
                .releaseDate(entity.getReleaseDate())
                .tagline(entity.getTagline())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .categories(categories)
                .build();
    }

    public ProductEntity mapToEntity(Product product) {
        if (product == null) {
            return null;
        }
        Set<CategoryEntity> categories = null;
        if (product.getCategories() != null) {
            categories = product.getCategories().stream()
                    .map(categoryMapper::mapToEntity)
                    .collect(toSet());
        }
        return ProductEntity.builder()
                .id(product.getId())
                .uuid(product.getUuid())
                .originalLanguage(product.getOriginalLanguage())
                .overview(product.getOverview())
                .posterPath(product.getPosterPath())
                .releaseDate(product.getReleaseDate())
                .tagline(product.getTagline())
                .title(product.getTitle())
                .price(product.getPrice())
                .categories(categories)
                .build();
    }
}
