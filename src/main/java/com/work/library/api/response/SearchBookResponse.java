package com.work.library.api.response;

import com.work.library.application.dto.result.BookResult;

import java.util.List;

/**
 * 조회한 도서의 응답
 *
 * @param id             도서 ID
 * @param title          도서 제목
 * @param author         도서 저자 이름
 * @param status         도서 상태
 * @param categories     도서에 속한 카테고리 목록
 */
public record SearchBookResponse(
        Long id,
        String title,
        String author,
        String status,
        List<SearchCategoryResponse> categories
) {
    public static SearchBookResponse from(BookResult result) {
        return new SearchBookResponse(
                result.id(),
                result.title(),
                result.author(),
                result.status(),
                SearchCategoryResponse.listFrom(result.categories())
        );
    }
}
