package com.work.library.application.dto.command;

import java.util.List;

/**
 * 도서의 카테고리 변경을 위한 커맨트 객체.
 *
 * @param bookId              도서 ID
 * @param newCategoryIdList   변경할 카테고리 ID 목록
 */
public record ChangeBookCategoriesCommand(
        Long bookId,
        List<Long> newCategoryIdList
) {
}
