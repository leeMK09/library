package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.book.RentalHistoryEntity;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository bookJpaRepository;

    private final BookCategoriesJpaRepository bookCategoriesJpaRepository;

    private final RentalHistoryJpaRepository rentalHistoryJpaRepository;

    public BookRepositoryImpl(
            BookJpaRepository bookJpaRepository,
            BookCategoriesJpaRepository bookCategoriesJpaRepository,
            RentalHistoryJpaRepository rentalHistoryJpaRepository
    ) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookCategoriesJpaRepository = bookCategoriesJpaRepository;
        this.rentalHistoryJpaRepository = rentalHistoryJpaRepository;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Optional<BookEntity> bookEntity = bookJpaRepository.findById(id);
        return bookEntity.map(entity -> {
            List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByBook(entity);
            Book result = toBookListBy(mappingEntities).getFirst();
            return result;
        });
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = book.toEntity();

        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        Author savedAuthor = new Author(savedBookEntity.getAuthor());

        Book savedBookDomain = savedBookEntity.toDomain(book.getCategories());
        BookCategories mappedBookCategories = mappedCategories(savedBookDomain);

        return new Book(savedBookEntity.getId(), savedBookEntity.getTitle(), savedAuthor, mappedBookCategories);
    }

    @Override
    public List<Book> findAllByCategories(List<Category> categories) {
        List<CategoryEntity> categoryEntities = categories.stream().map(Category::toRegisteredEntity).toList();
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByCategories(categoryEntities);
        return toBookListBy(mappingEntities);
    }

    @Override
    public List<Book> searchByTitleOrAuthor(String title, String author) {
        List<BookEntity> bookEntities = bookJpaRepository.searchByTitleOrAuthor(title, author);
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByBooks(bookEntities);

        return toBookListBy(mappingEntities);
    }

    @Override
    public Book remapCategoriesToBook(Book book, BookCategories newBookCategories) {
        unlinkCategoriesByBook(book);
        book.changeCategories(newBookCategories);
        mappedCategories(book);
        return book;
    }

    @Override
    public Optional<Book> searchByTitleAndAuthor(String title, String author) {
        Optional<BookEntity> bookEntity = bookJpaRepository.searchByTitleAndAuthor(title, author);
        return bookEntity.map(entity -> {
            List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByBook(entity);
            Book result = toBookListBy(mappingEntities).getFirst();
            return result;
        });
    }

    @Override
    public Book rental(Book book, LocalDateTime rentedAt, LocalDateTime expiredAt) {
        BookEntity bookEntity = book.toRegisteredEntity();
        RentalHistoryEntity rentalHistoryEntity = new RentalHistoryEntity(bookEntity, rentedAt, expiredAt);
        rentalHistoryJpaRepository.save(rentalHistoryEntity);
        return book;
    }

    private BookCategories mappedCategories(Book book) {
        BookCategories bookCategories = book.getCategories();
        List<BookCategoryMappingEntity> mappingEntities = bookCategories.toEntity(book.toRegisteredEntity());
        List<BookCategoryMappingEntity> savedMappingEntities = bookCategoriesJpaRepository.saveAll(mappingEntities);
        List<Category> categories = savedMappingEntities.stream().map(
                entity -> entity.getCategory().toDomain()
        ).toList();
        return new BookCategories(categories);
    }

    private Set<Map.Entry<BookEntity, List<CategoryEntity>>> groupedEntries(List<BookCategoryMappingEntity> mappingEntities) {
        Map<BookEntity, List<CategoryEntity>> result = mappingEntities.stream()
                .collect(Collectors.groupingBy(
                        BookCategoryMappingEntity::getBook,
                        Collectors.mapping(BookCategoryMappingEntity::getCategory, Collectors.toList())
                ));

        return result.entrySet();
    }

    private List<Book> toBookListBy(List<BookCategoryMappingEntity> mappingEntities) {
        Set<Map.Entry<BookEntity, List<CategoryEntity>>> entries = groupedEntries(mappingEntities);

        return entries.stream()
                .map(entry -> {
                    BookEntity bookEntity = entry.getKey();

                    List<Category> categoryList = entry.getValue().stream()
                            .map(CategoryEntity::toDomain)
                            .toList();
                    BookCategories bookCategories = new BookCategories(categoryList);

                    return bookEntity.toDomain(bookCategories);
                }).toList();
    }

    private void unlinkCategoriesByBook(Book book) {
        BookEntity entity = book.toRegisteredEntity();
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByBook(entity);
        bookCategoriesJpaRepository.deleteAll(mappingEntities);
    }
}
