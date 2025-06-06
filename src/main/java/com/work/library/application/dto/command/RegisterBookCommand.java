package com.work.library.application.dto.command;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.category.Category;

import java.util.List;

/**
 * 도서 등록을 위한 커맨드 객체
 *
 * @param title           도서 제목
 * @param author          도서 저자 이름
 * @param categoryIdList  도서가 속한 카테고리 ID 목록
 */
public record RegisterBookCommand(
        String title,
        String author,
        List<Long> categoryIdList
) {
    public Book toDomain(List<Category> categories) {
        return new Book(
                title,
                new Author(author),
                new BookCategories(categories)
        );
    }
}
