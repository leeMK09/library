package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.category.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class BookCategoriesJpaRepositoryTest {
    @Autowired
    private BookCategoriesJpaRepository repository;

    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Autowired
    private BookJpaRepository bookRepository;

    @Test
    void 매핑테이블을_저장하고_조회할_수_있다() {
        BookEntity bookEntity = BookCategoryMappingFixture.getBookEntity();
        CategoryEntity categoryEntity = BookCategoryMappingFixture.getCategoryEntity();
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        BookCategoryMappingEntity entity = new BookCategoryMappingEntity(savedBookEntity, savedCategoryEntity);

        BookCategoryMappingEntity savedEntity = repository.save(entity);
        BookCategoryMappingEntity result = repository.findById(savedEntity.getId()).orElseThrow();

        assertEquals(savedEntity.getId(), result.getId());
    }
}
