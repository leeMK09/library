package com.work.library.infrastructure.persistance.book;

import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCategoriesJpaRepository extends JpaRepository<BookCategoryMappingEntity, Long> {
    @Query("""
        SELECT DISTINCT m FROM BookCategoryMappingEntity m
        JOIN FETCH m.book
        JOIN FETCH m.category
        WHERE m.category IN :categories
    """)
    List<BookCategoryMappingEntity> findBooksByCategories(
            @Param("categories") List<CategoryEntity> categories
    );
}
