package com.sivalabs.videolibrary.catalog.adapter;

import com.sivalabs.videolibrary.catalog.domain.Category;
import com.sivalabs.videolibrary.catalog.domain.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findBySlug(String slug) {
        return jpaCategoryRepository.findBySlug(slug).map(categoryMapper::map);
    }

    @Override
    public List<Category> findAll() {
        var sort = Sort.by(ASC, "name");
        return jpaCategoryRepository.findAll(sort).stream().map(categoryMapper::map).toList();
    }

    @Override
    public Category save(Category category) {
        var entity = categoryMapper.mapToEntity(category);
        var savedCategory = jpaCategoryRepository.save(entity);
        return categoryMapper.map(savedCategory);
    }
}
