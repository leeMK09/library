package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
}
