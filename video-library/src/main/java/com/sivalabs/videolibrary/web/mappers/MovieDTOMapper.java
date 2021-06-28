package com.sivalabs.videolibrary.web.mappers;

import static com.sivalabs.videolibrary.core.utils.Constants.TMDB_IMAGE_PATH_PREFIX;

import com.sivalabs.videolibrary.core.entity.Movie;
import com.sivalabs.videolibrary.web.dto.MovieDTO;
import org.springframework.stereotype.Component;

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
