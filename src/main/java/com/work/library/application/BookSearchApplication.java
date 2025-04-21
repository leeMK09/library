package com.work.library.application;

import com.work.library.application.dto.result.SearchBookListResult;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Author;
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

    public List<SearchBookListResult> searchByCategoryIdList(List<Long> categoryIdList) {
        List<Category> foundCategories = categoryQueryService.findAllByIdList(categoryIdList);

        if (foundCategories.size() != CollectionUtils.getDistinctCount(categoryIdList)) {
            log.error(
                    "요청한 카테고리와 조회된 카테고리의 개수가 다릅니다. requested categories size : {}, found categories size : {}",
                    categoryIdList,
                    foundCategories.size()
            );
            throw BookApplicationException.invalidParameter();
        }

        List<Book> books = bookQueryService.findAllByCategories(foundCategories);

        log.info("==== 카테고리별 도서 조회 성공 ====");
        return SearchBookListResult.from(books);
    }

    public List<SearchBookListResult> searchByTileOrAuthor(String title, String author) {
        List<Book> books = bookQueryService.searchByTitleOrAuthor(title, new Author(author));

        log.info("==== 지은이, 책 제목 도서 조회 성공 ====");
        return SearchBookListResult.from(books);
    }
}
