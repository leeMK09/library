package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookEntity;
import com.work.library.entity.book.RentalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalHistoryJpaRepository extends JpaRepository<RentalHistoryEntity, Long> {
    Optional<RentalHistoryEntity> findByBook(BookEntity book);
}
