package com.work.library.api.response;

import com.work.library.application.dto.result.SearchBookResult;

import java.util.List;

/**
 * 조회한 도서의 목록 응답
 *
 * @param list             조회한 도서 목록
 */
public record SearchBookListResponse(
        List<SearchBookResponse> list
) {
    public static SearchBookListResponse from(List<SearchBookResult> results) {
        List<SearchBookResponse> list = results.stream().map(SearchBookResponse::from).toList();

        return new SearchBookListResponse(list);
    }
}
