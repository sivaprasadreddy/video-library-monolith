package com.sivalabs.videolibrary.catalog.adapter;

import com.sivalabs.videolibrary.catalog.adapter.CategoryEntity;
import com.sivalabs.videolibrary.catalog.domain.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category map(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getUuid(),
                entity.getName(),
                entity.getSlug()
        );
    }

    public CategoryEntity mapToEntity(Category category) {
        return new CategoryEntity(
                category.getId(),
                category.getUuid(),
                category.getName(),
                category.getSlug(),
                null
        );
    }
}
