package com.sivalabs.videolibrary.catalog.importer;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.sivalabs.videolibrary.ApplicationProperties;
import com.sivalabs.videolibrary.catalog.entity.Category;
import com.sivalabs.videolibrary.catalog.service.CatalogService;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDataImporterTest {
    private MovieRowMapper movieRowMapper;
    private ApplicationProperties applicationProperties;
    private CatalogService catalogService;
    private JsonMovieDataImporter movieDataImporter;

    @BeforeEach
    void setUp() {
        movieRowMapper = new MovieRowMapper();
        applicationProperties = new ApplicationProperties();
        applicationProperties.getDataImport().setDisabled(false);
        applicationProperties
                .getDataImport()
                .setMoviesDataFiles(singletonList("/data/movie-details-test.txt"));

        catalogService = mock(CatalogService.class);

        given(catalogService.saveCategory(any(Category.class)))
                .willAnswer(answer -> answer.getArgument(0));

        movieDataImporter =
                new JsonMovieDataImporter(catalogService, movieRowMapper, applicationProperties);
    }

    @Test
    void should_import_product_data_successfully() throws IOException {
        movieDataImporter.importData();
        assertTrue(true);
    }
}
