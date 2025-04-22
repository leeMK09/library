package com.work.library.infrastructure.persistance.category;

import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Category> findAllByIds(List<Long> idList) {
        List<CategoryEntity> result = categoryJpaRepository.findAllByIdIn(idList);
        return result.stream().map(CategoryEntity::toDomain).toList();
    }

    @Override
    public List<Category> findAll() {
        List<CategoryEntity> entities = categoryJpaRepository.findAll();
        return entities.stream().map(CategoryEntity::toDomain).toList();
    }
}
