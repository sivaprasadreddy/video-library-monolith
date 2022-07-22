package com.sivalabs.videolibrary.catalog.web;

import com.sivalabs.videolibrary.catalog.domain.CatalogService;
import com.sivalabs.videolibrary.catalog.domain.CategoryEntity;
import com.sivalabs.videolibrary.catalog.domain.ProductDTO;
import com.sivalabs.videolibrary.catalog.domain.ProductDTOMapper;
import com.sivalabs.videolibrary.common.logging.Loggable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
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
    private final ProductDTOMapper productDTOMapper;

    @GetMapping({"/"})
    public String home(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            Model model) {
        Page<ProductDTO> data;
        if (StringUtils.isEmpty(query)) {
            log.info("Fetching products with page: {}", pageNo);
            data = catalogService.findProducts(pageNo).map(productDTOMapper::map);
            model.addAttribute("paginationPrefix", "/?");
        } else {
            log.info("Searching products for {} with page: {}", query, pageNo);
            model.addAttribute("header", "Search results for \"" + query + "\"");
            model.addAttribute("paginationPrefix", "/?query=" + query);
            data = catalogService.searchProducts(query, pageNo).map(productDTOMapper::map);
        }

        model.addAttribute("page", data.getNumber() + 1);
        model.addAttribute("productsData", data);
        return "home";
    }

    @GetMapping({"/products/{id}"})
    public String viewBook(@PathVariable Long id, Model model) {
        ProductDTO product =
                catalogService.findProductById(id).map(productDTOMapper::map).orElse(null);
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/category/{slug}")
    public String byCategory(
            @PathVariable String slug,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNo,
            Model model) {
        log.info("Fetching products by category: {} with page: {}", slug, pageNo);
        Optional<CategoryEntity> optionalCategory = catalogService.findCategoryBySlug(slug);
        if (optionalCategory.isPresent()) {
            Page<ProductDTO> data =
                    catalogService
                            .findProductsByCategory(optionalCategory.get().getId(), pageNo)
                            .map(productDTOMapper::map);
            model.addAttribute(
                    "header", "Products by Category \"" + optionalCategory.get().getName() + "\"");
            model.addAttribute("productsData", data);
            model.addAttribute("page", data.getNumber() + 1);
        } else {
            model.addAttribute("header", "Products by Category \"" + slug + "\"");
            model.addAttribute("productsData", new ArrayList<>(0));
            model.addAttribute("page", 1);
        }
        model.addAttribute("paginationPrefix", "/category/" + slug + "?");
        return "home";
    }

    @ModelAttribute("categories")
    public List<CategoryEntity> allCategories() {
        return catalogService.findAllCategories();
    }
}
