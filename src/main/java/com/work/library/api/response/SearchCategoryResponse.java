package com.work.library.api.response;

import com.work.library.application.dto.result.SearchCategoryResult;

import java.util.List;

/**
 * 조회한 카테고리의 응답
 *
 * @param name  카테고리 이름
 */
public record SearchCategoryResponse(
        String name
) {
    public static List<SearchCategoryResponse> listFrom(List<SearchCategoryResult> results) {
        return results.stream().map(result -> new SearchCategoryResponse(result.name())).toList();
    }
}
