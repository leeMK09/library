package com.work.library.application;

import com.work.library.application.dto.SearchBookListResult;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.exception.ErrorType;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Author;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.category.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookSearchApplicationTest {

    @Mock
    private BookQueryService bookQueryService;

    @Mock
    private CategoryQueryService categoryQueryService;

    @InjectMocks
    private BookSearchApplication bookSearchApplication;

    @Test
    void 요청한_카테고리_ID_LIST_와_조회한_카테고리의_수가_다르면_예외가_발생한다() {
        List<Long> categoryIdList = List.of(1L, 2L);
        List<Category> foundCategories = List.of(new Category("문학"));

        when(categoryQueryService.findAllByIdList(categoryIdList))
                .thenReturn(foundCategories);

        BookApplicationException exception = assertThrows(BookApplicationException.class, () -> {
            bookSearchApplication.searchByCategoryIdList(categoryIdList);
        });
        assertEquals(ErrorType.INVALID_PARAMETER, exception.getType());
    }

    @Test
    void 유효한_카테고리_ID_LIST로_해당_카테고리에_속한_도서목록을_조회할_수_있다() {
        String title = "JPA";
        String author = "김영한";
        Category 문학 = new Category(1L, "문학");
        Category IT = new Category(2L, "IT");
        Book book = new Book(title, new Author(author), new BookCategories(List.of(문학, IT)));

        when(categoryQueryService.findAllByIdList(List.of(문학.getId(), IT.getId())))
                .thenReturn(List.of(문학, IT));
        when(bookQueryService.findAllByCategories(List.of(문학, IT)))
                .thenReturn(List.of(book));
        List<SearchBookListResult> searchBookListResult = bookSearchApplication.searchByCategoryIdList(List.of(문학.getId(), IT.getId()));
        SearchBookListResult result = searchBookListResult.getFirst();

        assertEquals(1, searchBookListResult.size());
        assertEquals(title, result.title());
        assertEquals(author, result.author());
        assertEquals(2, result.categories().size());
        assertEquals("문학", result.categories().get(0).name());
        assertEquals("IT", result.categories().get(1).name());
    }
}