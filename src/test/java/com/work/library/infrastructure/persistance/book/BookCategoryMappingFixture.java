package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;

public class BookCategoryMappingFixture {
    public static BookEntity getBookEntity() {
        return new BookEntity("김영한", "JPA");
    }

    public static CategoryEntity getCategoryEntity() {
        return new CategoryEntity("문학");
    }

    public static CategoryEntity createCategoryEntityBy(String name) {
        return new CategoryEntity(name);
    }
}
