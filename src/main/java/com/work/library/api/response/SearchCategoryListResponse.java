package com.work.library.api.response;

import com.work.library.application.dto.result.SearchCategoryResult;

import java.util.List;

/**
 * 조회한 카테고리 목록 응답
 *
 * @param list             조회한 카테고리 목록
 */
public record SearchCategoryListResponse(
        List<SearchCategoryResponse> list
) {
    public static SearchCategoryListResponse from(List<SearchCategoryResult> results) {
        List<SearchCategoryResponse> list = SearchCategoryResponse.listFrom(results);

        return new SearchCategoryListResponse(list);
    }
}
