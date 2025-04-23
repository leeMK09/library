package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.RentalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalHistoryJpaRepository extends JpaRepository<RentalHistoryEntity, Long> {
}
