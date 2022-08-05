package com.sivalabs.videolibrary.config;

import com.sivalabs.videolibrary.catalog.importer.JsonMovieDataImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final JsonMovieDataImporter movieDataImporter;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing movies data");
        movieDataImporter.importData();
        log.debug("Movies data initialized successfully");
    }
}
