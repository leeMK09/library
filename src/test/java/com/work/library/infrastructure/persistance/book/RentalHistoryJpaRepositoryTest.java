package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.book.RentalHistoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
class RentalHistoryJpaRepositoryTest {
    @Autowired
    private RentalHistoryJpaRepository rentalHistoryJpaRepository;

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Test
    void 대여_히스토리를_저장하고_조회할_수_있다() {
        BookEntity bookEntity = new BookEntity("JPA", "김영한");
        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        RentalHistoryEntity rentalHistoryEntity = new RentalHistoryEntity(
                savedBookEntity,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );
        RentalHistoryEntity savedRentalHistoryEntity = rentalHistoryJpaRepository.save(rentalHistoryEntity);

        RentalHistoryEntity result = rentalHistoryJpaRepository.findById(savedRentalHistoryEntity.getId()).orElseThrow();

        assertEquals(savedRentalHistoryEntity.getId(), result.getId());
    }
}