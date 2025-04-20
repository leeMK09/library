package com.work.library.infrastructure.persistance.category;

import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryRepositoryImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = category.toEntity();
        CategoryEntity savedEntity = categoryJpaRepository.save(entity);

        return savedEntity.toDomain();
    }
}
