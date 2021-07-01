package com.sivalabs.videolibrary.datafactory;

import com.sivalabs.videolibrary.catalog.entity.Genre;
import com.sivalabs.videolibrary.catalog.entity.Movie;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.sivalabs.videolibrary.catalog.importer.MovieRowMapperUtils.toSlug;

public class TestDataFactory {


    public static Movie createMovie(String title, String... genreNames) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenres(
                Arrays.stream(genreNames)
                        .map(
                                g -> {
                                    Genre genre = new Genre();
                                    genre.setName(g);
                                    genre.setSlug(toSlug(g));
                                    return genre;
                                })
                        .collect(Collectors.toSet()));
        return movie;
    }
}
