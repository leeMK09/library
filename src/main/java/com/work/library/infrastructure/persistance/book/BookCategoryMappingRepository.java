package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookCategoryMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCategoryMappingRepository extends JpaRepository<BookCategoryMappingEntity, Long> {
    @Query("""
        SELECT m
        FROM BookCategoryMappingEntity m
        JOIN m.book b
        WHERE b.id = :bookId
    """)
    List<BookCategoryMappingEntity> findByBookId(@Param("bookId") Long bookId);
}
