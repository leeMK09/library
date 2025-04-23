package com.work.library.entity.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.book.BookCategoriesJpaRepository;
import com.work.library.infrastructure.persistance.book.BookJpaRepository;
import com.work.library.infrastructure.persistance.category.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class BookCategoryMappingEntityTest {
    @Autowired
    private BookCategoriesJpaRepository bookCategoriesJpaRepository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Test
    void 도서를_통해_매핑된_엔티티를_조회할_수_있다() {
        BookEntity bookEntity = new BookEntity("JPA", "김영한");
        CategoryEntity categoryEntity = new CategoryEntity("IT");
        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        CategoryEntity savedCategoryEntity = categoryJpaRepository.save(categoryEntity);
        BookCategoryMappingEntity mappingEntity = new BookCategoryMappingEntity(savedBookEntity, savedCategoryEntity);
        bookCategoriesJpaRepository.save(mappingEntity);

        List<BookCategoryMappingEntity> mappings = bookCategoriesJpaRepository.findAllByBook(savedBookEntity);

        mappings.forEach(entity -> {
            assertTrue(entity.isMappedTo(savedBookEntity));
        });
    }
}
