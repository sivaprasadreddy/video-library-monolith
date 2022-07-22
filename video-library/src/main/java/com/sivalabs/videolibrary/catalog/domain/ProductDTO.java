package com.sivalabs.videolibrary.catalog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String title;

    @JsonProperty("tmdb_id")
    private Long tmdbId;

    private String overview;
    private String tagline;

    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("original_language")
    private String originalLanguage;

    private BigDecimal price;
    private Set<CategoryEntity> categories;

    public String getTrimmedTitle() {
        return StringUtils.abbreviate(title, 30);
    }

    public String getTrimmedOverview() {
        return StringUtils.abbreviate(overview, 100);
    }

    public String getTrimmedTagline() {
        return StringUtils.abbreviate(tagline, 100);
    }
}
