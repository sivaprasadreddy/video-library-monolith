package com.sivalabs.videolibrary;

import com.sivalabs.videolibrary.catalog.importer.DataImportProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(DataImportProperties.class)
public class TestApplication {}
