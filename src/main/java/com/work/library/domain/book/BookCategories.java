package com.work.library.domain.book;

import com.work.library.domain.book.exception.BookCategoriesException;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;

import java.util.List;

public class BookCategories {
    private final List<Category> categories;

    public BookCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            throw BookCategoriesException.blankCategories();
        }

        if (hasDuplicated(categories)) {
            throw BookCategoriesException.duplicateCategory();
        }

        this.categories = categories;
    }

    public int size() {
        return this.categories.size();
    }

    public List<BookCategoryMappingEntity> toEntity(BookEntity bookEntity) {
        List<CategoryEntity> categoryEntities = this.categories.stream().map(Category::toRegisteredEntity).toList();

        return categoryEntities.stream().map(
                categoryEntity -> new BookCategoryMappingEntity(bookEntity, categoryEntity)
        ).toList();
    }

    private boolean hasDuplicated(List<Category> categories) {
        return categories.size() != categories.stream().distinct().count();
    }
}
