package com.work.library.domain.category.repository;

import com.work.library.domain.category.Category;

import java.util.List;

public interface CategoryRepository {
    Category save(Category category);

    List<Category> findAllByIdList(List<Long> idList);

    List<Category> findAll();
}
