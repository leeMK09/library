package com.work.library.application.service;

import java.time.LocalDateTime;

public interface RentalPolicy {
    LocalDateTime getRentedAt();

    LocalDateTime getExpiredAt(LocalDateTime rentedAt);
}
