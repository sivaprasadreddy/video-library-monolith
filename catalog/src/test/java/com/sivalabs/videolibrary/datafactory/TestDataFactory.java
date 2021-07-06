package com.sivalabs.videolibrary.datafactory;

import static com.sivalabs.videolibrary.common.utils.CommonUtils.toSlug;

import com.sivalabs.videolibrary.catalog.entity.Category;
import com.sivalabs.videolibrary.catalog.entity.Product;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestDataFactory {

    public static Product createProduct(String title, String... categories) {
        Product product = new Product();
        product.setTitle(title);
        product.setCategories(
                Arrays.stream(categories)
                        .map(
                                g -> {
                                    Category category = new Category();
                                    category.setName(g);
                                    category.setSlug(toSlug(g));
                                    return category;
                                })
                        .collect(Collectors.toSet()));
        return product;
    }
}
