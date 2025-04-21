package com.work.library.infrastructure.persistance.book;

import com.work.library.annotations.RepositoryTest;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.repository.BookRepository;
import com.work.library.domain.category.Category;
import com.work.library.domain.category.repository.CategoryRepository;
import com.work.library.entity.book.BookCategoryMappingEntity;
import com.work.library.entity.category.CategoryEntity;
import com.work.library.infrastructure.persistance.category.CategoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
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
        Book 문학_IT_카테고리_도서 = bookRepository.save(book1);
        Book 인문학_카테고리_도서 = bookRepository.save(book2);

        assertTrue(
                문학_IT_카테고리_도서.getCategories().getNames().contains(
                        "문학"
                )
        );
        assertTrue(
                문학_IT_카테고리_도서.getCategories().getNames().contains(
                        "IT"
                )
        );
        assertTrue(
                인문학_카테고리_도서.getCategories().getNames().contains(
                        "인문학"
                )
        );
    }

    @Test
    void 지은이와_책_제목으로_도서를_검색할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Category category = new Category("문학");
        Category savedCategory = categoryRepository.save(category);
        BookCategories bookCategories = new BookCategories(List.of(savedCategory));
        Book book = new Book(title, author, bookCategories);
        bookRepository.save(book);

        List<Book> foundBookList = bookRepository.searchByTitleOrAuthor(title, author);
        Book result = foundBookList.getFirst();

        assertEquals(title, result.getTitle());
        assertEquals(author.value(), result.getAuthor());
    }

    @Test
    void 지은이_혹은_책_제목_중_하나가_없더라도_일치하는_도서를_검색할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Category category = new Category("문학");
        Category savedCategory = categoryRepository.save(category);
        BookCategories bookCategories = new BookCategories(List.of(savedCategory));
        Book book = new Book(title, author, bookCategories);
        bookRepository.save(book);

        List<Book> foundBookListWithoutAuthor = bookRepository.searchByTitleOrAuthor(title, null);
        List<Book> foundBookListWithoutTitle = bookRepository.searchByTitleOrAuthor(null, author);
        Book foundBookWithoutAuthor = foundBookListWithoutAuthor.getFirst();
        Book foundBookWithoutTitle = foundBookListWithoutTitle.getFirst();

        assertEquals(title, foundBookWithoutTitle.getTitle());
        assertEquals(title, foundBookWithoutAuthor.getTitle());
        assertEquals(author.value(), foundBookWithoutTitle.getAuthor());
        assertEquals(author.value(), foundBookWithoutAuthor.getAuthor());
    }

    @Test
    void 도서와_카테고리를_변경할_수_있다() {
        String title = "JPA";
        Author author = new Author("김영한");
        Category oldCategory = new Category("문학");
        Category newCategory = new Category("IT");
        Category savedOldCategory = categoryRepository.save(oldCategory);
        Category savedNewCategory = categoryRepository.save(newCategory);
        BookCategories bookCategories = new BookCategories(List.of(savedOldCategory));
        Book book = new Book(title, author, bookCategories);
        Book savedBook = bookRepository.save(book);

        BookCategories newBookCategories = new BookCategories(List.of(savedNewCategory));
        bookRepository.remapCategoriesToBook(savedBook, newBookCategories);
        List<BookCategoryMappingEntity> entities = bookCategoriesJpaRepository.findAllByBook(savedBook.toRegisteredEntity());
        List<CategoryEntity> categories = entities.stream().map(BookCategoryMappingEntity::getCategory).toList();
        List<String> result = categories.stream().map(CategoryEntity::getName).toList();

        assertTrue(
                result.contains(
                        newCategory.getName()
                )
        );
        assertFalse(
                result.contains(
                        oldCategory.getName()
                )
        );
    }
}
