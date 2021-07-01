package com.sivalabs.videolibrary.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.sivalabs.videolibrary.catalog.entity.Movie;
import com.sivalabs.videolibrary.catalog.service.MovieService;
import com.sivalabs.videolibrary.catalog.web.controller.MovieController;
import com.sivalabs.videolibrary.catalog.web.mappers.MovieDTOMapper;
import com.sivalabs.videolibrary.common.AbstractMvcUnitTest;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest extends AbstractMvcUnitTest {

    @MockBean private MovieService movieService;

    @SpyBean private MovieDTOMapper movieDTOMapper;

    @Test
    void shouldShowHomePage() throws Exception {
        Page<Movie> page = new PageImpl<>(new ArrayList<>());
        given(movieService.findMovies(any(Pageable.class))).willReturn(page);
        this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"));
    }
}
