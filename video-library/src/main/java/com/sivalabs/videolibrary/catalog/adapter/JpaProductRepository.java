package com.sivalabs.videolibrary.catalog.adapter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("select distinct m from ProductEntity m inner join m.categories c where c.id = :categoryId")
    Page<ProductEntity> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    Page<ProductEntity> findByTitleContainingIgnoreCase(String query, Pageable pageable);
}
