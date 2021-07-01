package com.sivalabs.videolibrary.catalog.web.mappers;

import com.sivalabs.videolibrary.catalog.entity.Movie;
import com.sivalabs.videolibrary.catalog.web.dto.MovieDTO;
import org.springframework.stereotype.Component;

import static com.sivalabs.videolibrary.common.utils.Constants.TMDB_IMAGE_PATH_PREFIX;

@Component
public class MovieDTOMapper {

    public MovieDTO map(Movie movie) {
        if (movie == null) {
            return null;
        }
        return MovieDTO.builder()
                .id(movie.getId())
                .budget(movie.getBudget())
                .homepage(movie.getHomepage())
                .tmdbId(movie.getTmdbId())
                .originalLanguage(movie.getOriginalLanguage())
                .overview(movie.getOverview())
                .posterPath(TMDB_IMAGE_PATH_PREFIX + movie.getPosterPath())
                .releaseDate(movie.getReleaseDate())
                .revenue(movie.getRevenue())
                .tagline(movie.getTagline())
                .runtime(movie.getRuntime())
                .title(movie.getTitle())
                .price(movie.getPrice())
                .genres(movie.getGenres())
                .build();
    }
}
