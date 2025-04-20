package com.work.library.entity.category;

import com.work.library.domain.category.Category;
import com.work.library.entity.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    @Comment("카테고리 이름")
    private String name;

    protected CategoryEntity() {}

    public CategoryEntity(String name) {
        this.name = name;
    }

    public CategoryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category toDomain() {
        return new Category(id, name);
    }
}
