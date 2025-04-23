package com.work.library.application.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRentalPolicyTest {
    private DefaultRentalPolicy rentalPolicy = new DefaultRentalPolicy();

    @Test
    void 대여시간은_오늘이어야_한다() {
        LocalDate today = LocalDate.now();

        LocalDateTime rentedAt = rentalPolicy.getRentedAt();

        assertEquals(today, rentedAt.toLocalDate());
    }

    @Test
    void 대여만료시간은_요청일로부터_30일_이후_여야한다() {
        LocalDateTime rentedAt = rentalPolicy.getRentedAt();
        LocalDateTime expiredAt = rentalPolicy.getExpiredAt(rentedAt);

        assertEquals(rentedAt.plusDays(30).toLocalDate(), expiredAt.toLocalDate());
    }
}