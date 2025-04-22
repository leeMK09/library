package com.work.library.application;

import com.work.library.application.dto.result.CategoryResult;
import com.work.library.application.service.CategoryQueryService;
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

    List<CategoryResult> findAll() {

    }
}
