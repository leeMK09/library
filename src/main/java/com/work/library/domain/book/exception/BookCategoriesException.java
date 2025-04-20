package com.work.library.domain.book.exception;

import com.work.library.domain.ErrorMessage;

public class BookCategoriesException extends RuntimeException {
    public BookCategoriesException(String message) {
        super(message);
    }

    public static BookCategoriesException blankCategories() {
        return new BookCategoriesException(ErrorMessage.BOOK_CATEGORIES_BLANK);
    }
}
