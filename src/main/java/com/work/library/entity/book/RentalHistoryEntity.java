package com.work.library.entity.book;

import com.work.library.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental_histories")
public class RentalHistoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @Comment("대여한 책")
    private BookEntity book;

    @Column(name = "rented_at", nullable = false)
    @Comment("대여한 시간")
    private LocalDateTime rentedAt;

    @Column(name = "expired_at", nullable = false)
    @Comment("대여 만료 시간")
    private LocalDateTime expiredAt;

    protected RentalHistoryEntity() {}

    public RentalHistoryEntity(BookEntity book, LocalDateTime rentedAt, LocalDateTime expiredAt) {
        this.book = book;
        this.rentedAt = rentedAt;
        this.expiredAt = expiredAt;
    }

    public Long getId() {
        return id;
    }

    public BookEntity getBook() {
        return book;
    }

    public LocalDateTime getRentedAt() {
        return rentedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
