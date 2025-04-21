package com.work.library.domain.category;

import com.work.library.domain.category.exception.InvalidCategoryException;
import com.work.library.entity.category.CategoryEntity;

import java.util.Objects;

public class Category {
    private Long id;

    private final String name;

    public Category(Long id, String name) {
        if (name == null || name.isBlank()) {
            throw InvalidCategoryException.blankName();
        }

        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw InvalidCategoryException.blankName();
        }

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEntity toRegisteredEntity() {
        return new CategoryEntity(id, name);
    }

    public CategoryEntity toEntity() {
        return new CategoryEntity(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
