package com.sivalabs.videolibrary.catalog.importer;

import static com.sivalabs.videolibrary.common.utils.TimeUtils.millisToLongDHMS;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sivalabs.videolibrary.ApplicationProperties;
import com.sivalabs.videolibrary.catalog.domain.CatalogService;
import com.sivalabs.videolibrary.catalog.domain.CategoryEntity;
import com.sivalabs.videolibrary.catalog.domain.ProductEntity;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonMovieDataImporter {
    private final CatalogService catalogService;
    private final MovieRowMapper movieRowMapper;
    private final ApplicationProperties applicationProperties;

    @Async
    public void importDataAsync() throws IOException {
        importDataInternal();
    }

    public void importData() throws IOException {
        importDataInternal();
    }

    private void importDataInternal() throws IOException {
        deleteExistingProductsData();
        importProductsMetaData();
    }

    private void deleteExistingProductsData() {
        catalogService.cleanupProductsData();
    }

    private void importProductsMetaData() throws IOException {
        log.info(
                "Initializing products database from files: {}",
                applicationProperties.getDataImport().getMoviesDataFiles());
        long start = System.currentTimeMillis();
        long recordCount = 0;
        for (String dataFile : applicationProperties.getDataImport().getMoviesDataFiles()) {
            recordCount += importProductsFromJsonFile(dataFile, recordCount);
        }
        long end = System.currentTimeMillis();
        log.debug("Time took for importing products : {} ", millisToLongDHMS(end - start));
    }

    private long importProductsFromJsonFile(String fileName, long recordCount) throws IOException {
        log.info("Importing products from file: {}", fileName);
        ClassPathResource file = new ClassPathResource(fileName, this.getClass());
        List<String> lines = IOUtils.readLines(file.getInputStream(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper =
                new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Map<String, CategoryEntity> genresMap =
                catalogService.findAllCategories().stream()
                        .collect(Collectors.toMap(CategoryEntity::getName, g -> g));
        List<ProductEntity> productsBatches = new ArrayList<>();

        log.info("Line count:{}", lines.size());
        for (String line : lines) {
            MovieJsonRecord movieJsonRecord = objectMapper.readValue(line, MovieJsonRecord.class);
            ProductEntity product = movieRowMapper.mapToMovieEntity(movieJsonRecord);
            product.setCategories(saveCategories(genresMap, product.getCategories()));
            productsBatches.add(product);
            recordCount++;
            if (applicationProperties.getDataImport().getMaxSize() > 0
                    && recordCount >= applicationProperties.getDataImport().getMaxSize()) {
                break;
            }
            if (productsBatches.size() >= applicationProperties.getDataImport().getBatchSize()) {
                catalogService.createProducts(productsBatches);
                log.debug("Imported {} products so far", recordCount);
                productsBatches = new ArrayList<>();
            }
        }

        if (!productsBatches.isEmpty()) {
            catalogService.createProducts(productsBatches);
            log.debug("Imported {} products so far", recordCount);
        }
        log.info("Imported products with {} records from file {}", recordCount, fileName);
        return recordCount;
    }

    private Set<CategoryEntity> saveCategories(
            Map<String, CategoryEntity> existingCategories, Set<CategoryEntity> categories) {
        Set<CategoryEntity> categoryList = new HashSet<>();
        for (CategoryEntity category : categories) {
            CategoryEntity existingCategory = existingCategories.get(category.getName());
            if (existingCategory != null) {
                categoryList.add(existingCategory);
            } else {
                CategoryEntity savedCategory = catalogService.saveCategory(category);
                categoryList.add(savedCategory);
                existingCategories.put(savedCategory.getName(), savedCategory);
            }
        }
        return categoryList;
    }
}
