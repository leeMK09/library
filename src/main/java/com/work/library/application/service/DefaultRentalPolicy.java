package com.work.library.application.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DefaultRentalPolicy implements RentalPolicy {

    private final int RENTAL_EXPIRED_DAY_OFFSET = 30;

    @Override
    public LocalDateTime getRentedAt() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime getExpiredAt(LocalDateTime rentedAt) {
        return rentedAt.plusDays(RENTAL_EXPIRED_DAY_OFFSET);
    }
}
