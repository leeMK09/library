package com.work.library.api.response;

import com.work.library.application.dto.result.BookResult;

import java.util.List;

/**
 * 도서의 카테고리 변경 응답
 *
 * @param bookId             도서의 ID
 * @param categories         변경한 도서의 카테고리 응답
 */
public record ChangeBookCategoriesResponse(
        Long bookId,
        List<SearchCategoryResponse> categories
) {
    public static ChangeBookCategoriesResponse from(BookResult result) {
        return new ChangeBookCategoriesResponse(
                result.id(),
                SearchCategoryResponse.listFrom(result.categories())
        );
    }
}
