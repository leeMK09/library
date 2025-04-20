package com.work.library.domain.category;

import com.work.library.domain.category.exception.InvalidCategoryException;

public class Category {
    private final String name;

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw InvalidCategoryException.blankName();
        }

        this.name = name;
    }
}
