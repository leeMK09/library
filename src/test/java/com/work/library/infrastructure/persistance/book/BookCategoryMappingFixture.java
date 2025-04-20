package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;

import java.util.List;

public class BookCategoryMappingFixture {
    public static BookEntity getBookEntity() {
        return new BookEntity("김영한", "JPA");
    }

    public static CategoryEntity getCategoryEntity() {
        return new CategoryEntity("문학");
    }

    public static List<CategoryEntity> createCategoriesBy(List<String> names) {
        return names.stream().map(CategoryEntity::new).toList();
    }

    public static List<BookCategoryMappingEntity> createBy(BookEntity bookEntity, List<CategoryEntity> categories) {
        return categories.stream().map(
                (categoryEntity) -> new BookCategoryMappingEntity(bookEntity, categoryEntity)
        ).toList();
    }
}
