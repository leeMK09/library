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

    // * 사용하는 도메인 기준, 매핑 테이블을 통한 카테고리 정보는 필수
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    protected BookCategoryMappingEntity() {}

    public BookCategoryMappingEntity(BookEntity book, CategoryEntity category) {
        this.book = book;
        this.category = category;
    }
}
