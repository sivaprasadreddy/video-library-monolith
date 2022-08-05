package com.sivalabs.videolibrary.catalog.web;

import com.sivalabs.videolibrary.catalog.domain.CatalogService;
import com.sivalabs.videolibrary.catalog.domain.Category;
import com.sivalabs.videolibrary.catalog.domain.Product;
import com.sivalabs.videolibrary.common.logging.Loggable;
import com.sivalabs.videolibrary.common.models.PagedResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
@Loggable
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping({"/"})
    public String home(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            Model model) {
        PagedResult<Product> data;
        if (StringUtils.isEmpty(query)) {
            log.info("Fetching products with page: {}", pageNo);
            data = catalogService.findProducts(pageNo);
            model.addAttribute("paginationPrefix", "/?");
        } else {
            log.info("Searching products for {} with page: {}", query, pageNo);
            model.addAttribute("header", "Search results for \"" + query + "\"");
            model.addAttribute("paginationPrefix", "/?query=" + query);
            data = catalogService.searchProducts(query, pageNo);
        }

        model.addAttribute("page", data.getPageNumber());
        model.addAttribute("productsData", data);
        return "home";
    }

    @GetMapping({"/products/{id}"})
    public String viewBook(@PathVariable Long id, Model model) {
        Product product = catalogService.findProductById(id).orElse(null);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/category/{slug}")
    public String byCategory(
            @PathVariable String slug,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            Model model) {
        log.info("Fetching products by category: {} with page: {}", slug, pageNo);
        Optional<Category> optionalCategory = catalogService.findCategoryBySlug(slug);
        if (optionalCategory.isPresent()) {
            PagedResult<Product> data =
                    catalogService.findProductsByCategory(optionalCategory.get().getId(), pageNo);
            model.addAttribute(
                    "header", "Products by Category \"" + optionalCategory.get().getName() + "\"");
            model.addAttribute("productsData", data);
            model.addAttribute("page", data.getPageNumber());
        } else {
            model.addAttribute("header", "Products by Category \"" + slug + "\"");
            model.addAttribute("productsData", new ArrayList<>(0));
            model.addAttribute("page", 1);
        }
        model.addAttribute("paginationPrefix", "/category/" + slug + "?");
        return "home";
    }

    @ModelAttribute("categories")
    public List<Category> allCategories() {
        return catalogService.findAllCategories();
    }
}
