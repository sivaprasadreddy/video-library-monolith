package com.sivalabs.videolibrary.catalog.importer;

import com.sivalabs.videolibrary.catalog.domain.Category;
import com.sivalabs.videolibrary.catalog.domain.Product;
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
    public static final String TMDB_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500";
    private static final Random random = new Random();

    public Product mapToMovieEntity(MovieJsonRecord movieJsonRecord) {
        Product product = new Product();
        product.setTitle(movieJsonRecord.getTitle());
        product.setUuid(movieJsonRecord.getId());
        product.setPosterPath(TMDB_IMAGE_PATH_PREFIX + movieJsonRecord.getPosterPath());
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

    private Set<Category> convertToCategories(List<MovieJsonRecord.Genre> genres) {
        Set<Category> categorySet = new HashSet<>();
        if (genres != null) {
            for (MovieJsonRecord.Genre genre : genres) {
                Category category = new Category();
                category.setUuid((long) genre.getId());
                category.setSlug(CommonUtils.toSlug(genre.getName()));
                category.setName(genre.getName());

                categorySet.add(category);
            }
        }
        return categorySet;
    }
}
