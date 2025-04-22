package com.work.library.api;

import com.work.library.api.base.Response;
import com.work.library.api.response.SearchCategoryResponse;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.category.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @GetMapping
    public Response<SearchCategoryResponse> findAll() {
        List<Category> categories = categoryQueryService.findAll();
    }
}
