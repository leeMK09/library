package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository bookJpaRepository;

    private final BookCategoriesJpaRepository bookCategoriesJpaRepository;

    public BookRepositoryImpl(
            BookJpaRepository bookJpaRepository,
            BookCategoriesJpaRepository bookCategoriesJpaRepository
    ) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookCategoriesJpaRepository = bookCategoriesJpaRepository;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<BookEntity> bookEntity = bookJpaRepository.findById(id);

        return bookEntity.map(this::toBookDomain);
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = book.getId() != null ? book.toRegisteredEntity() : book.toEntity();
        BookCategories bookCategories = book.getCategories();

        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        mappedCategories(bookEntity, bookCategories);
        return savedBookEntity.toDomain(book.getCategories());
    }

    @Override
    public Book update(Book book) {
        BookEntity entity = book.toRegisteredEntity();
        BookEntity savedBookEntity = bookJpaRepository.save(entity);
        return savedBookEntity.toDomain(book.getCategories());
    }

    @Override
    public Optional<Book> searchByTitleAndAuthor(String title, String author) {
        Optional<BookEntity> bookEntity = bookJpaRepository.searchByTitleAndAuthor(title, author);

        return bookEntity.map(this::toBookDomain);
    }

    @Override
    public List<Book> searchAllByTitleOrAuthor(String title, String author) {
        List<BookEntity> bookEntities = bookJpaRepository.searchByTitleOrAuthor(title, author);

        return toBookDomain(bookEntities);
    }

    @Override
    public List<Book> findAllByCategories(List<Category> categories) {
        List<CategoryEntity> categoryEntities = categories.stream().map(Category::toRegisteredEntity).toList();
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByCategories(categoryEntities);
        List<BookEntity> bookEntities = mappingEntities.stream().map(BookCategoryMappingEntity::getBook).toList();

        return toBookDomain(bookEntities);
    }

    @Override
    public void deleteAllMappingsByBook(Book book) {
        BookEntity bookEntity = book.toRegisteredEntity();
        List<BookCategoryMappingEntity> mappings = bookCategoriesJpaRepository.findAllByBook(bookEntity);
        bookCategoriesJpaRepository.deleteAll(mappings);
    }

    private Book toBookDomain(BookEntity entity) {
        List<CategoryEntity> categoryEntities = getMappedCategories(entity);
        BookCategories bookCategories = BookCategories.fromEntities(categoryEntities);
        return entity.toDomain(bookCategories);
    }

    private List<Book> toBookDomain(List<BookEntity> bookEntities) {
        List<BookCategoryMappingEntity> mappings = bookCategoriesJpaRepository.findAllByBooks(bookEntities);

        return bookEntities.stream()
                .map(bookEntity -> {
                    List<CategoryEntity> categoryEntities = mappings.stream()
                            .filter(mapping -> mapping.isMappedTo(bookEntity))
                            .map(BookCategoryMappingEntity::getCategory)
                            .toList();

                    BookCategories bookCategories = BookCategories.fromEntities(categoryEntities);

                    return bookEntity.toDomain(bookCategories);
                })
                .toList();
    }

    private List<CategoryEntity> getMappedCategories(BookEntity entity) {
        List<BookCategoryMappingEntity> mappings = bookCategoriesJpaRepository.findAllByBook(entity);
        List<CategoryEntity> result = mappings.stream().map(BookCategoryMappingEntity::getCategory).toList();
        return result;
    }

    private void mappedCategories(BookEntity bookEntity, BookCategories bookCategories) {
        List<BookCategoryMappingEntity> mappingEntities = bookCategories.toEntity(bookEntity);
        bookCategoriesJpaRepository.saveAll(mappingEntities);
    }
}
