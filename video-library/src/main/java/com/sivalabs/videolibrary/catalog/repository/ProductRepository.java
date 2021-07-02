package com.sivalabs.videolibrary.catalog.repository;

import com.sivalabs.videolibrary.catalog.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select distinct m from Product m inner join m.categories c where c.id = :categoryId")
    Page<Product> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    Page<Product> findByTitleContainingIgnoreCase(String query, Pageable pageable);

    Optional<Product> findByTmdbId(Long tmdbId);
}
