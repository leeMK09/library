package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.RentalHistory;
import com.work.library.domain.book.repository.RentalHistoryRepository;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.category.CategoryJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
@Import(RentalHistoryRepositoryImpl.class)
class RentalHistoryRepositoryTest {
    @Autowired
    private RentalHistoryRepository repository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private Book savedBook;

    @BeforeEach
    void setUp() {
        CategoryEntity categoryEntity = new CategoryEntity("IT");
        CategoryEntity savedCategoryEntity = categoryJpaRepository.save(categoryEntity);
        Category category = savedCategoryEntity.toDomain();
        BookCategories bookCategories = new BookCategories(List.of(category));
        Book book = new Book("JPA", new Author("김영한"), bookCategories);
        BookEntity savedBookEntity = bookJpaRepository.save(book.toEntity());
        savedBook = savedBookEntity.toDomain(bookCategories);
    }

    @Test
    void 대여_이력_정보를_저장할_수_있다() {
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime expiredAt = rentedAt.plusDays(30);
        RentalHistory rentalHistory = new RentalHistory(savedBook, rentedAt, expiredAt);

        RentalHistory savedRentalHistory = repository.save(rentalHistory);

        assertEquals(rentedAt, savedRentalHistory.getRentedAt());
        assertEquals(expiredAt, savedRentalHistory.getExpiredAt());
    }
}
