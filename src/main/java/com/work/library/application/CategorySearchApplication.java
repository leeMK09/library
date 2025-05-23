package com.work.library.application;

import com.work.library.application.dto.result.CategoryResult;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.category.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategorySearchApplication {

    private final CategoryQueryService categoryQueryService;

    public CategorySearchApplication(CategoryQueryService categoryQueryService) {
        this.categoryQueryService = categoryQueryService;
    }

    public List<CategoryResult> findAll() {
        List<Category> categories = categoryQueryService.findAll();
        return categories.stream().map(category -> new CategoryResult(category.getName())).toList();
    }
}
