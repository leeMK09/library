package com.work.library.domain.book;

import com.work.library.domain.category.Category;
import com.work.library.entity.book.RentalHistoryEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RentalHistoryTest {

    @Test
    void 도메인에_올바른_엔티티를_생성할_수_있다() {
        Book book = new Book("JPA", new Author("김영한"), new BookCategories(List.of(new Category("IT"))));
        LocalDateTime rentedAt = LocalDateTime.now();
        LocalDateTime expiredAt = rentedAt.plusDays(30);
        RentalHistory rentalHistory = new RentalHistory(book, rentedAt, expiredAt);

        RentalHistoryEntity entity = rentalHistory.toEntity();

        assertNotNull(entity);
        assertEquals(book.getAuthor(), entity.getBook().getAuthor());
        assertEquals(book.getTitle(), entity.getBook().getTitle());
        assertEquals(rentedAt, entity.getRentedAt());
        assertEquals(expiredAt, entity.getExpiredAt());
    }
}
