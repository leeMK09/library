package com.work.library.application.dto.result;

import com.work.library.domain.book.Book;

import java.util.List;

public record SearchBookListResult(
        Long id,
        String title,
        String author,
        List<CategoryResult> categories
) {
    public static List<SearchBookListResult> from(List<Book> books) {
        List<SearchBookListResult> result = books.stream().map(book -> {
            List<String> categoryNames = book.getCategories().getNames();
            List<CategoryResult> categoryResult = categoryNames.stream().map(CategoryResult::new).toList();

            return new SearchBookListResult(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    categoryResult
            );
        }).toList();

        return result;
    }
}
