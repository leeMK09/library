package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookCategoriesRepository;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookCategoriesRepositoryImpl implements BookCategoriesRepository {
    private final BookCategoriesJpaRepository bookCategoriesJpaRepository;

    public BookCategoriesRepositoryImpl(BookCategoriesJpaRepository bookCategoriesJpaRepository) {
        this.bookCategoriesJpaRepository = bookCategoriesJpaRepository;
    }

    @Override
    public BookCategories save(Book book, BookCategories bookCategories) {
        BookEntity bookEntity = book.toRegisteredEntity();
        List<BookCategoryMappingEntity> mappingEntities = bookCategories.toEntity(bookEntity);
        List<BookCategoryMappingEntity> savedEntities = bookCategoriesJpaRepository.saveAll(mappingEntities);
        List<Category> categories = savedEntities.stream().map(
                entity -> entity.getCategory().toDomain()
        ).toList();

        return new BookCategories(categories);
    }
}
