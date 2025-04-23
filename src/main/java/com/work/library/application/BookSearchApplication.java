package com.work.library.application;

import com.work.library.application.dto.result.BookResult;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Book;
import com.work.library.domain.category.Category;
import com.work.library.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookSearchApplication {
    private final BookQueryService bookQueryService;
    private final CategoryQueryService categoryQueryService;

    public BookSearchApplication(
            BookQueryService bookQueryService,
            CategoryQueryService categoryQueryService
    ) {
        this.bookQueryService = bookQueryService;
        this.categoryQueryService = categoryQueryService;
    }

    public List<BookResult> searchByCategoryIds(List<Long> categoryIds) {
        List<Category> foundCategories = categoryQueryService.findAllByIds(categoryIds);

        if (!CollectionUtils.isEqualsSize(foundCategories, categoryIds)) {
            log.error(
                    "[BookSearchApplication] 요청한 카테고리와 조회된 카테고리의 개수가 다릅니다. requested categories size : {}, found categories size : {}",
                    categoryIds,
                    foundCategories.size()
            );
            throw BookApplicationException.notFoundCategories();
        }

        List<Book> books = bookQueryService.findAllByCategories(foundCategories);

        log.info("[BookSearchApplication] 카테고리별 도서 조회 성공");
        return BookResult.listFrom(books);
    }

    public List<BookResult> searchByTileOrAuthor(String title, String author) {
        List<Book> books = bookQueryService.searchAllByTitleOrAuthor(title, author);

        log.info("[BookSearchApplication] 지은이, 책 제목 기준 도서 조회 성공");
        return BookResult.listFrom(books);
    }
}
