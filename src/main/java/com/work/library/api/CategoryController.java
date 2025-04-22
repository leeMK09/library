package com.work.library.api;

import com.work.library.api.base.Response;
import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.api.response.SearchCategoryListResponse;
import com.work.library.application.CategorySearchApplication;
import com.work.library.application.dto.result.CategoryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategorySearchApplication categorySearchApplication;

    public CategoryController(CategorySearchApplication categorySearchApplication) {
        this.categorySearchApplication = categorySearchApplication;
    }

    @GetMapping
    public Response<SearchCategoryListResponse> findAll() {
        List<CategoryResult> results = categorySearchApplication.findAll();
        SearchCategoryListResponse response = SearchCategoryListResponse.from(results);

        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.CategoryResponseMessage.SUCCESS_CATEGORIES_READ,
                response
        );
    }
}
