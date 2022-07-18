package com.sivalabs.videolibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableAsync
@EnableScheduling
@EnableCaching
public class VideoLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoLibraryApplication.class, args);
    }
}
