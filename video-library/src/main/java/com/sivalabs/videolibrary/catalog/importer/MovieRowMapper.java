package com.sivalabs.videolibrary.catalog.importer;

import com.sivalabs.videolibrary.catalog.domain.CategoryEntity;
import com.sivalabs.videolibrary.catalog.domain.ProductEntity;
import com.sivalabs.videolibrary.common.utils.CommonUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MovieRowMapper {

    private static final Random random = new Random();

    public ProductEntity mapToMovieEntity(MovieJsonRecord movieJsonRecord) {
        ProductEntity product = new ProductEntity();
        product.setTitle(movieJsonRecord.getTitle());
        product.setTmdbId(movieJsonRecord.getId());
        product.setPosterPath(movieJsonRecord.getPosterPath());
        product.setOverview(movieJsonRecord.getOverview());
        product.setTagline(movieJsonRecord.getTagline());
        product.setReleaseDate(toLocalDate(movieJsonRecord.getReleaseDate()));
        product.setOriginalLanguage(movieJsonRecord.getOriginalLanguage());
        product.setPrice(randomPrice());
        product.setCategories(convertToCategories(movieJsonRecord.getGenres()));
        return product;
    }

    private BigDecimal randomPrice() {
        int min = 10, max = 100;
        return new BigDecimal(random.nextInt((max - min) + 1) + min);
    }

    private LocalDate toLocalDate(String dateString) {
        if (StringUtils.trimToNull(dateString) == null) return null;
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private Set<CategoryEntity> convertToCategories(List<MovieJsonRecord.Genre> genres) {
        Set<CategoryEntity> categorySet = new HashSet<>();
        if (genres != null) {
            for (MovieJsonRecord.Genre genre : genres) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setTmdbId((long) genre.getId());
                categoryEntity.setSlug(CommonUtils.toSlug(genre.getName()));
                categoryEntity.setName(genre.getName());

                categorySet.add(categoryEntity);
            }
        }
        return categorySet;
    }
}
