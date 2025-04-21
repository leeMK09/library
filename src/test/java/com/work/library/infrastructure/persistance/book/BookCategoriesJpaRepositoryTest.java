package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.category.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    void 카테고리별_도서목록을_조회할_수_있다() {
        String categoryName = "IT";
        BookEntity bookEntity = BookCategoryMappingFixture.getBookEntity();
        CategoryEntity categoryEntity = BookCategoryMappingFixture.createCategoryEntityBy(categoryName);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        BookCategoryMappingEntity entity = new BookCategoryMappingEntity(savedBookEntity, savedCategoryEntity);
        repository.save(entity);

        List<BookCategoryMappingEntity> mappingEntities = repository.findAllByCategories(List.of(savedCategoryEntity));
        List<BookEntity> bookEntities = mappingEntities.stream().map(BookCategoryMappingEntity::getBook).toList();
        List<CategoryEntity> categoryEntities = mappingEntities.stream().map(BookCategoryMappingEntity::getCategory).toList();

        assertTrue(bookEntities.contains(savedBookEntity));
        assertTrue(categoryEntities.contains(savedCategoryEntity));
        assertEquals(1, mappingEntities.size());
        assertEquals(categoryName, mappingEntities.get(0).getCategory().getName());
        assertEquals(savedBookEntity.getId(), mappingEntities.get(0).getBook().getId());
    }

    @Test
    void 도서별로_할당된_카테고리_목록을_조회할_수_있다() {
        String categoryName = "IT";
        BookEntity bookEntity = BookCategoryMappingFixture.getBookEntity();
        CategoryEntity categoryEntity = BookCategoryMappingFixture.createCategoryEntityBy(categoryName);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        BookCategoryMappingEntity entity = new BookCategoryMappingEntity(savedBookEntity, savedCategoryEntity);
        repository.save(entity);

        List<BookCategoryMappingEntity> mappingEntities = repository.findAllByBooks(List.of(savedBookEntity));
        List<BookEntity> bookEntities = mappingEntities.stream().map(BookCategoryMappingEntity::getBook).toList();
        List<CategoryEntity> categoryEntities = mappingEntities.stream().map(BookCategoryMappingEntity::getCategory).toList();

        assertTrue(bookEntities.contains(savedBookEntity));
        assertTrue(categoryEntities.contains(savedCategoryEntity));
        assertEquals(categoryName, mappingEntities.get(0).getCategory().getName());
    }

    @Test
    void 도서_하나의_속한_카테고리_목록을_조회할_수_있다() {
        String IT = "IT";
        String 문학 = "문학";
        BookEntity bookEntity = BookCategoryMappingFixture.getBookEntity();
        CategoryEntity IT_카테고리 = BookCategoryMappingFixture.createCategoryEntityBy(IT);
        CategoryEntity 문학_카테고리 = BookCategoryMappingFixture.createCategoryEntityBy(문학);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        CategoryEntity saved_IT_카테고리 = categoryRepository.save(IT_카테고리);
        CategoryEntity saved_문학_카테고리 = categoryRepository.save(문학_카테고리);
        BookCategoryMappingEntity mappedEntity1 = new BookCategoryMappingEntity(savedBookEntity, saved_IT_카테고리);
        BookCategoryMappingEntity mappedEntity2 = new BookCategoryMappingEntity(savedBookEntity, saved_문학_카테고리);
        repository.saveAll(List.of(mappedEntity1, mappedEntity2));

        List<BookCategoryMappingEntity> result = repository.findAllByBook(savedBookEntity);

        assertEquals(2, result.size());
        assertEquals(IT, result.get(0).getCategory().getName());
        assertEquals(문학, result.get(1).getCategory().getName());
    }
}
