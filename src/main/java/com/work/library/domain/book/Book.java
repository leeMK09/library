package com.work.library.domain.book;

import com.work.library.domain.book.exception.BookException;

public class Book {
    private final String title;

    private final Author author;

    private final BookCategories categories;

    public Book(String title, Author author, BookCategories categories) {
        if (title == null || title.isBlank()) {
            throw BookException.blankTitle();
        }

        if (author == null) {
            throw BookException.emptyAuthor();
        }

        if (categories == null) {
            throw BookException.emptyCategories();
        }

        this.title = title;
        this.author = author;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author.value();
    }
}
