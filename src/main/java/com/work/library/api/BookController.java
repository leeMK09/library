package com.work.library.api;

import com.work.library.api.base.Response;
import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.api.response.SearchBookListResponse;
import com.work.library.application.BookSearchApplication;
import com.work.library.application.dto.result.SearchBookResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookSearchApplication bookSearchApplication;

    public BookController(BookSearchApplication bookSearchApplication) {
        this.bookSearchApplication = bookSearchApplication;
    }

    @GetMapping("/categories")
    public Response<SearchBookListResponse> searchBooksByCategoryIds(
            @RequestParam(value = "ids", required = true) List<Long> categoryIds
    ) {
        List<SearchBookResult> results = bookSearchApplication.searchByCategoryIdList(categoryIds);
        SearchBookListResponse response = SearchBookListResponse.from(results);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ,
                response
        );
    }

    @GetMapping
    public Response<SearchBookListResponse> searchBooksByTitleOrAuthor(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author
    ) {
        List<SearchBookResult> results = bookSearchApplication.searchByTileOrAuthor(title, author);
        SearchBookListResponse response = SearchBookListResponse.from(results);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ,
                response
        );
    }
}
