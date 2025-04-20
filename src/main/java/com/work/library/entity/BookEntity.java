package com.work.library.entity;

import com.work.library.domain.book.BookStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    @Comment("책 제목")
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    @Comment("지은이")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;

    protected BookEntity() {}

    public BookEntity(String author, String title) {
        this.author = author;
        this.title = title;
    }
}
