package com.sivalabs.videolibrary.catalog.repository;

import com.sivalabs.videolibrary.catalog.entity.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository
        extends JpaRepository<com.sivalabs.videolibrary.catalog.entity.Genre, Long> {

    Optional<com.sivalabs.videolibrary.catalog.entity.Genre> findByName(String genre);

    Optional<Genre> findBySlug(String slug);
}
