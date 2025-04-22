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
public record SearchBookResult(
        Long id,
        String title,
        String author,
        List<SearchCategoryResult> categories
) {
    public static List<SearchBookResult> listFrom(List<Book> books) {
        List<SearchBookResult> result = books.stream()
                .map(book -> {
                    List<String> categoryNames = book.getCategories().getNames();
                    List<SearchCategoryResult> categoryResult = categoryNames.stream().map(SearchCategoryResult::new).toList();

                    return new SearchBookResult(
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            categoryResult
                    );
                }).toList();

        return result;
    }
}
