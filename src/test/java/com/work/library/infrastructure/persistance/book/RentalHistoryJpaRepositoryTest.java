package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.book.RentalHistoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void 삭제한_대여히스토리를_조회되지_않는다() {
        BookEntity bookEntity1 = new BookEntity("JPA1", "김영한1");
        BookEntity bookEntity2 = new BookEntity("JPA2", "김영한2");
        BookEntity savedBookEntity1 = bookJpaRepository.save(bookEntity1);
        BookEntity savedBookEntity2 = bookJpaRepository.save(bookEntity2);
        RentalHistoryEntity rentalHistoryEntity1 = new RentalHistoryEntity(
                savedBookEntity1,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );
        RentalHistoryEntity rentalHistoryEntity2 = new RentalHistoryEntity(
                savedBookEntity2,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );
        rentalHistoryJpaRepository.save(rentalHistoryEntity1);
        rentalHistoryJpaRepository.save(rentalHistoryEntity2);

        rentalHistoryJpaRepository.delete(rentalHistoryEntity1);
        bookJpaRepository.delete(savedBookEntity1);
        List<RentalHistoryEntity> entities = rentalHistoryJpaRepository.findAll();

        assertEquals(1, entities.size());
    }

    @Test
    void 대여한_책을_통해_대여이력을_조회할_수_있다() {
        BookEntity bookEntity = new BookEntity("JPA1", "김영한1");
        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        RentalHistoryEntity rentalHistoryEntity = new RentalHistoryEntity(
                savedBookEntity,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30)
        );
        rentalHistoryJpaRepository.save(rentalHistoryEntity);

        RentalHistoryEntity result = rentalHistoryJpaRepository.findByBook(savedBookEntity).orElseThrow();
        BookEntity foundBookByResult = result.getBook();

        assertEquals(savedBookEntity.getId(), foundBookByResult.getId());
    }
}