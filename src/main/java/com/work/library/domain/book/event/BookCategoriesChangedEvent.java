package com.work.library.domain.book.event;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;

import java.time.LocalDateTime;

/**
 * 도서의 카테고리 변경을 위한 도메인 이벤트.
 *
 * @param book                변경된 도서
 * @param timestamp           이벤트 발행 시간
 */
public record BookCategoriesChangedEvent(
        Book book,
        LocalDateTime timestamp
) {
}
