package com.work.library.application.dto.result;

import com.work.library.domain.book.Book;

import java.util.List;

/**
 * 조회한 도서의 결과
 *
 * @param id             도서 ID
 * @param title          도서 제목
 * @param author         도서 저자 이름
 * @param categories     도서에 속한 카테고리 목록
 */
public record BookResult(
        Long id,
        String title,
        String author,
        List<CategoryResult> categories
) {
    public static List<BookResult> listFrom(List<Book> books) {
        List<BookResult> result = books.stream()
                .map(book -> {
                    List<String> categoryNames = book.getCategories().getNames();
                    List<CategoryResult> categoryResult = categoryNames.stream().map(CategoryResult::new).toList();

                    return new BookResult(
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            categoryResult
                    );
                }).toList();

        return result;
    }

    public static BookResult from(Book book) {
        List<String> categoryNames = book.getCategories().getNames();
        return new BookResult(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                categoryNames.stream().map(CategoryResult::new).toList()
        );
    }
}
