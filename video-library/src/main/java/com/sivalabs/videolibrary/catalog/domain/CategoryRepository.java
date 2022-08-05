package com.sivalabs.videolibrary.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> findBySlug(String slug);

    List<Category> findAll();

    Category save(Category category);
}
