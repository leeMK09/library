package com.work.library.infrastructure.persistance;

import com.work.library.entity.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
