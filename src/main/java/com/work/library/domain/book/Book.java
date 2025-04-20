package com.work.library.domain.book;

import com.work.library.domain.book.exception.InvalidBookException;

public class Book {
    private final String title;

    private final Author author;

    public Book(String title, Author author) {
        if (title == null || title.isBlank()) {
            throw InvalidBookException.blankTitle();
        }

        if (author == null) {
            throw InvalidBookException.emptyAuthor();
        }

        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author.value();
    }
}
