package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookCategoryMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoriesJpaRepository extends JpaRepository<BookCategoryMappingEntity, Long> {
}
