package com.sivalabs.videolibrary;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationProperties {

    private DataImport dataImport = new DataImport();

    @Data
    public static class DataImport {
        private boolean disabled;
        private int maxSize;
        private int batchSize;
        private boolean async;
        private List<String> moviesDataFiles;
    }
}
