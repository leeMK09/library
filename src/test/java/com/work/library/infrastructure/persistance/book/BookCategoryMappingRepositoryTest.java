package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class BookCategoryMappingRepositoryTest {
    @Autowired
    private BookCategoryMappingRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

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
    void 책에_속한_카테고리_정보를_가져올_수_있다() {
        BookEntity bookEntity = BookCategoryMappingFixture.getBookEntity();
        List<CategoryEntity> categories = BookCategoryMappingFixture.createCategoriesBy(
                List.of(
                        "문학",
                        "IT",
                        "경제경영",
                        "인문학",
                        "과학"
                )
        );
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        List<CategoryEntity> savedCategoryEntities = categoryRepository.saveAll(categories);
        List<BookCategoryMappingEntity> entities =
                BookCategoryMappingFixture.createBy(savedBookEntity, savedCategoryEntities);
        repository.saveAll(entities);

        List<BookCategoryMappingEntity> result = repository.findByBookId(savedBookEntity.getId());
        List<CategoryEntity> foundCategories = result.stream().map(BookCategoryMappingEntity::getCategory).toList();

        assertEquals(categories.size(), foundCategories.size());
    }
}
