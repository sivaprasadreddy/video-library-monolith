package com.sivalabs.videolibrary.catalog.web.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.sivalabs.videolibrary.catalog.entity.Category;
import com.sivalabs.videolibrary.catalog.mappers.ProductDTOMapper;
import com.sivalabs.videolibrary.catalog.model.ProductDTO;
import com.sivalabs.videolibrary.catalog.service.CatalogService;
import com.sivalabs.videolibrary.common.logging.Loggable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
            @PageableDefault(size = 24)
                    @SortDefault.SortDefaults({
                        @SortDefault(sort = "releaseDate", direction = DESC),
                        @SortDefault(sort = "title", direction = ASC)
                    })
                    Pageable pageable,
            Model model) {
        Page<ProductDTO> data;
        if (StringUtils.isEmpty(query)) {
            log.info("Fetching products with page: {}", pageable.getPageNumber());
            data = catalogService.findProducts(pageable).map(productDTOMapper::map);
            model.addAttribute("paginationPrefix", "/?");
        } else {
            log.info("Searching products for {} with page: {}", query, pageable.getPageNumber());
            model.addAttribute("header", "Search results for \"" + query + "\"");
            model.addAttribute("paginationPrefix", "/?query=" + query);
            data = catalogService.searchProducts(query, pageable).map(productDTOMapper::map);
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
            @PageableDefault(size = 24)
                    @SortDefault.SortDefaults({
                        @SortDefault(sort = "releaseDate", direction = DESC),
                        @SortDefault(sort = "title", direction = ASC)
                    })
                    Pageable pageable,
            Model model) {
        log.info("Fetching products by category: {} with page: {}", slug, pageable.getPageNumber());
        Optional<Category> optionalCategory = catalogService.findCategoryBySlug(slug);
        if (optionalCategory.isPresent()) {
            Page<ProductDTO> data =
                    catalogService
                            .findProductsByCategory(optionalCategory.get().getId(), pageable)
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
    public List<Category> allCategories() {
        Sort sort = Sort.by(ASC, "name");
        return catalogService.findAllCategories(sort);
    }
}
