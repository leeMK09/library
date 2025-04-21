package com.work.library.entity.book;

import com.work.library.entity.BaseEntity;
import com.work.library.entity.category.CategoryEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "book_category_mappings")
public class BookCategoryMappingEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    protected BookCategoryMappingEntity() {}

    public BookCategoryMappingEntity(BookEntity book, CategoryEntity category) {
        this.book = book;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public CategoryEntity getCategory() {
        return category;
    }
}
