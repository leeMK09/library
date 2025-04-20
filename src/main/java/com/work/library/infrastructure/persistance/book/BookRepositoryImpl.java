package com.work.library.infrastructure.persistance.book;

import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookCategoriesRepository;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.entity.book.BookEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final BookJpaRepository bookJpaRepository;

    private final BookCategoriesRepository bookCategoriesRepository;

    public BookRepositoryImpl(
            BookJpaRepository bookJpaRepository,
            BookCategoriesRepository bookCategoriesRepository
    ) {
        this.bookJpaRepository = bookJpaRepository;
        this.bookCategoriesRepository = bookCategoriesRepository;
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

    private BookCategories mappedCategories(Book book) {
        return bookCategoriesRepository.save(book, book.getCategories());
    }
}
