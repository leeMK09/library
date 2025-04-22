package com.work.library.domain.category.repository;

import com.work.library.domain.category.Category;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);

    List<Category> findAllByIds(List<Long> ids);

    List<Category> findAll();
}
