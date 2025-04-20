package com.work.library.entity.book;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.BookStatus;
import com.work.library.entity.BaseEntity;
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

    public BookEntity(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public BookEntity(Long id, String title, String author, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Book toDomain(BookCategories categories) {
        Author authorDomain = new Author(author);
        return new Book(id, title, authorDomain, categories);
    }
}
