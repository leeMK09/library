package com.work.library.domain.book;

import com.work.library.entity.book.RentalHistoryEntity;

import java.time.LocalDateTime;

public class RentalHistory {
    private Book book;

    private LocalDateTime rentedAt;

    private LocalDateTime expiredAt;

    public RentalHistory(Book book, LocalDateTime rentedAt, LocalDateTime expiredAt) {
        this.book = book;
        this.rentedAt = rentedAt;
        this.expiredAt = expiredAt;
    }

    public RentalHistoryEntity toEntity() {
        return new RentalHistoryEntity(
                book.toRegisteredEntity(),
                rentedAt,
                expiredAt
        );
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
