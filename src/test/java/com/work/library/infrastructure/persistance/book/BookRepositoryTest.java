package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.infrastructure.persistance.category.CategoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RepositoryTest
@Import({BookRepositoryImpl.class, BookCategoriesRepositoryImpl.class, CategoryRepositoryImpl.class})
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private List<Category> savedCategories;

    @BeforeEach
    void setUp() {
        List<Category> initCategories = List.of(
                new Category("문학"),
                new Category("IT"),
                new Category("인문학")
        );

        savedCategories = initCategories.stream()
                        .map(categoryRepository::save)
                        .toList();
    }

    @Test
    void 도서와_도서의_카테고리를_저장할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        BookCategories bookCategories = new BookCategories(savedCategories);
        Book book = new Book(title, author, bookCategories);

        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals(title, savedBook.getTitle());
        assertEquals(author.value(), savedBook.getAuthor());
        assertEquals(bookCategories.size(), savedBook.getCategories().size());
    }
}
