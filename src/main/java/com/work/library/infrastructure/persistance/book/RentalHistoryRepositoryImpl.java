package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.RentalHistory;
import com.work.library.domain.book.repository.RentalHistoryRepository;
import com.work.library.entity.book.RentalHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RentalHistoryRepositoryImpl implements RentalHistoryRepository {

    private final RentalHistoryJpaRepository rentalHistoryJpaRepository;

    public RentalHistoryRepositoryImpl(RentalHistoryJpaRepository rentalHistoryJpaRepository) {
        this.rentalHistoryJpaRepository = rentalHistoryJpaRepository;
    }

    @Override
    public RentalHistory save(RentalHistory rentalHistory) {
        RentalHistoryEntity entity = rentalHistory.toEntity();
        rentalHistoryJpaRepository.save(entity);
        return rentalHistory;
    }
}
