package com.work.library.domain.book;

import com.work.library.domain.book.exception.BookCategoriesException;
import com.work.library.domain.category.Category;

import java.util.List;

public class BookCategories {
    private final List<Category> categories;

    public BookCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            throw BookCategoriesException.blankCategories();
        }
        this.categories = categories;
    }
}
