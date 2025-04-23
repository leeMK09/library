package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.infrastructure.persistance.category.CategoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RepositoryTest
@Import({BookRepositoryImpl.class, CategoryRepositoryImpl.class})
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoriesJpaRepository bookCategoriesJpaRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private List<Category> savedCategories;

    List<Category> initCategories = List.of(
            new Category("문학"),
            new Category("IT"),
            new Category("인문학")
    );

    @BeforeEach
    void setUp() {
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

    @Nested
    class Read {
        @Test
        void 카테고리별_도서_목록을_조회할_수_있다() {
            String title = "JPA";
            Author author = new Author("김영한");
            BookCategories 문학_IT_카테고리 = new BookCategories(
                    List.of(
                            savedCategories.getFirst(),
                            savedCategories.get(1)
                    )
            );
            BookCategories 인문학_카테고리 = new BookCategories(
                    List.of(
                            savedCategories.get(2)
                    )
            );
            Book book1 = new Book(title, author, 문학_IT_카테고리);
            Book book2 = new Book(title, author, 인문학_카테고리);
            bookRepository.save(book1);
            bookRepository.save(book2);

            List<Book> 문학_IT_카테고리_도서 = bookRepository.findAllByCategories(List.of(
                    savedCategories.getFirst(),
                    savedCategories.get(1)
            ));
            List<Book> 인문학_카테고리_도서 = bookRepository.findAllByCategories(List.of(
                    savedCategories.get(2)
            ));

            List<String> list1 = 문학_IT_카테고리_도서.stream()
                    .flatMap(entity -> entity.getCategories().getNames().stream())
                    .toList();
            List<String> list2 = 인문학_카테고리_도서.stream()
                    .flatMap(entity -> entity.getCategories().getNames().stream())
                    .toList();
            assertTrue(
                    list1.contains(
                            "문학"
                    )
            );
            assertTrue(
                    list1.contains(
                            "IT"
                    )
            );
            assertTrue(
                    list2.contains(
                            "인문학"
                    )
            );
        }

        @Test
        void 지은이와_책_제목으로_도서를_검색할_수_있다() {
            String title = "JPA";
            String author = "김영한";
            Category category = new Category("문학");
            Category savedCategory = categoryRepository.save(category);
            BookCategories bookCategories = new BookCategories(List.of(savedCategory));
            Book book = new Book(title, new Author(author), bookCategories);
            bookRepository.save(book);

            List<Book> foundBooks = bookRepository.searchAllByTitleOrAuthor(title, author);
            Book result = foundBooks.getFirst();

            assertEquals(title, result.getTitle());
            assertEquals(author, result.getAuthor());
        }

        @Test
        void 지은이_혹은_책_제목_중_하나가_없더라도_일치하는_도서를_검색할_수_있다() {
            String title = "JPA";
            String author = "김영한";
            Category category = new Category("문학");
            Category savedCategory = categoryRepository.save(category);
            BookCategories bookCategories = new BookCategories(List.of(savedCategory));
            Book book = new Book(title, new Author(author), bookCategories);
            bookRepository.save(book);

            List<Book> foundBookListWithoutAuthor = bookRepository.searchAllByTitleOrAuthor(title, null);
            List<Book> foundBookListWithoutTitle = bookRepository.searchAllByTitleOrAuthor(null, author);
            Book foundBookWithoutAuthor = foundBookListWithoutAuthor.getFirst();
            Book foundBookWithoutTitle = foundBookListWithoutTitle.getFirst();

            assertEquals(title, foundBookWithoutTitle.getTitle());
            assertEquals(title, foundBookWithoutAuthor.getTitle());
            assertEquals(author, foundBookWithoutTitle.getAuthor());
            assertEquals(author, foundBookWithoutAuthor.getAuthor());
        }

        @Test
        void 지은이와_제목에_일치하는_도서를_조회할_수_있다() {
            String title = "JPA";
            String author = "김영한";
            Category category = new Category("IT");
            Category savedCategory = categoryRepository.save(category);
            BookCategories bookCategories = new BookCategories(List.of(savedCategory));
            Book book = new Book(title, new Author(author), bookCategories);
            bookRepository.save(book);

            Book foundBook = bookRepository.searchByTitleAndAuthor(title, author).orElseThrow();

            assertEquals(title, foundBook.getTitle());
            assertEquals(author, foundBook.getAuthor());
        }
    }

    @Nested
    class Update {
        @Test
        void 도서에_속한_카테고리_매핑정보를_삭제할_수_있다() {
            String title = "JPA";
            String author = "김영한";
            BookCategories bookCategories = new BookCategories(savedCategories);
            Book book = new Book(title, new Author(author), bookCategories);
            Book savedBook = bookRepository.save(book);
            bookCategoriesJpaRepository.saveAll(bookCategories.toEntity(savedBook.toRegisteredEntity()));

            bookRepository.deleteAllMappingsByBook(savedBook);
            List<BookCategoryMappingEntity> mappings = bookCategoriesJpaRepository.findAllByBook(savedBook.toRegisteredEntity());

            assertTrue(mappings.isEmpty());
        }
    }
}
