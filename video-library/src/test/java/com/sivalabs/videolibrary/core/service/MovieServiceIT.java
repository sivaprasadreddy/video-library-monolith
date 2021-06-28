package com.sivalabs.videolibrary.core.service;

import static com.sivalabs.videolibrary.core.utils.Constants.PROFILE_IT;
import static org.assertj.core.api.Assertions.assertThat;

import com.sivalabs.videolibrary.common.AbstractIntegrationTest;
import com.sivalabs.videolibrary.core.repository.MovieRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({PROFILE_IT})
public class MovieServiceIT extends AbstractIntegrationTest {

    @Autowired private MovieService movieService;

    @Autowired private MovieRepository movieRepository;

    @Autowired EntityManager entityManager;

    @Test
    void shouldCleanUpData() {
        movieService.cleanupMovieData();
        assertThat(movieRepository.count()).isEqualTo(0);
    }
}
