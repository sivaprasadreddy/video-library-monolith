package com.sivalabs.videolibrary.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    private Long id;
    private String title;
    private Long uuid;
    private String overview;
    private String tagline;
    private LocalDate releaseDate;
    private String posterPath;
    private String originalLanguage;
    private Set<Category> categories;
    private BigDecimal price;

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
