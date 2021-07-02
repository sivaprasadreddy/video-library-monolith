package com.sivalabs.videolibrary.catalog.repository;

import com.sivalabs.videolibrary.catalog.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
}
