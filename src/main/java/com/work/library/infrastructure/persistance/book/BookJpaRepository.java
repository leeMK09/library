package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
    @Query("""
        SELECT b FROM BookEntity b
        WHERE (:title IS NULL OR b.title LIKE %:title%)
        AND (:author IS NULL OR b.author LIKE %:author%)
    """)
    List<BookEntity> searchByTitleOrAuthor(String title, String author);

    Optional<BookEntity> searchByTitleAndAuthor(String title, String author);
}
