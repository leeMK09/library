package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.book.BookEntity;
import com.work.library.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Book save(Book book) {
        BookEntity bookEntity = book.toEntity();

        BookEntity savedBookEntity = bookJpaRepository.save(bookEntity);
        Author savedAuthor = new Author(savedBookEntity.getAuthor());

        Book savedBookDomain = savedBookEntity.toDomain(book.getCategories());
        BookCategories mappedBookCategories = mappedCategories(savedBookDomain);

        return new Book(savedBookEntity.getId(), savedBookEntity.getTitle(), savedAuthor, mappedBookCategories);
    }

    @Override
    public List<Book> findAllByCategoryList(List<Category> categories) {
        List<CategoryEntity> categoryEntities = categories.stream().map(Category::toRegisteredEntity).toList();
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByCategories(categoryEntities);
        return toBookListBy(mappingEntities);
    }

    @Override
    public List<Book> searchByTitleOrAuthor(String title, Author author) {
        String searchAuthor = author != null ? author.value() : null;
        List<BookEntity> bookEntities = bookJpaRepository.searchByTitleOrAuthor(title, searchAuthor);
        List<BookCategoryMappingEntity> mappingEntities = bookCategoriesJpaRepository.findAllByBooks(bookEntities);

        return toBookListBy(mappingEntities);
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

        return entries.stream().map(entry -> {
            BookEntity bookEntity = entry.getKey();

            List<Category> categoryList = entry.getValue().stream()
                    .map(CategoryEntity::toDomain)
                    .toList();
            BookCategories bookCategories = new BookCategories(categoryList);

            return bookEntity.toDomain(bookCategories);
        }).toList();
    }

}
