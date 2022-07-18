package com.sivalabs.videolibrary.catalog.importer;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
class MovieJsonRecord {

    private Long id;
    private String title;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    private String tagline;
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;

    private List<Genre> genres;

    @Data
    public static class Genre {
        private Integer id;
        private String name;
    }
}
