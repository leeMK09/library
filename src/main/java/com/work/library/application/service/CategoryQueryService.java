package com.work.library.application.service;

import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryQueryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllByIds(List<Long> ids) {
        return categoryRepository.findAllByIds(ids);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
