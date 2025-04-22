package com.work.library.api;

import com.work.library.api.base.Response;
import com.work.library.api.base.ResponseMessage;
import com.work.library.api.base.ResponseType;
import com.work.library.api.request.ChangeBookCategoriesRequest;
import com.work.library.api.request.RegisterBookRequest;
import com.work.library.api.response.ChangeBookCategoriesResponse;
import com.work.library.api.response.ChangeBookResponse;
import com.work.library.api.response.RegisteredBookResponse;
import com.work.library.api.response.SearchBookListResponse;
import com.work.library.application.BookCategoriesUpdateApplication;
import com.work.library.application.BookRegisterApplication;
import com.work.library.application.BookSearchApplication;
import com.work.library.application.BookUpdateApplication;
import com.work.library.application.dto.result.BookResult;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookSearchApplication bookSearchApplication;

    private final BookRegisterApplication bookRegisterApplication;

    private final BookCategoriesUpdateApplication bookCategoriesUpdateApplication;

    private final BookUpdateApplication bookUpdateApplication;

    public BookController(
            BookSearchApplication bookSearchApplication,
            BookRegisterApplication bookRegisterApplication,
            BookCategoriesUpdateApplication bookCategoriesUpdateApplication,
            BookUpdateApplication bookUpdateApplication
    ) {
        this.bookSearchApplication = bookSearchApplication;
        this.bookRegisterApplication = bookRegisterApplication;
        this.bookCategoriesUpdateApplication = bookCategoriesUpdateApplication;
        this.bookUpdateApplication = bookUpdateApplication;
    }

    @GetMapping("/categories")
    public Response<SearchBookListResponse> searchBooksByCategoryIds(
            @RequestParam(value = "ids", required = true) List<Long> categoryIds
    ) {
        List<BookResult> results = bookSearchApplication.searchByCategoryIds(categoryIds);
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
        List<BookResult> results = bookSearchApplication.searchByTileOrAuthor(title, author);
        SearchBookListResponse response = SearchBookListResponse.from(results);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_READ,
                response
        );
    }

    @PostMapping
    public Response<RegisteredBookResponse> save(
            @Valid @RequestBody RegisterBookRequest request
    ) {
        Long savedId = bookRegisterApplication.save(request.toCommand());
        RegisteredBookResponse response = new RegisteredBookResponse(savedId);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_SAVE,
                response
        );
    }

    @PatchMapping("/{bookId}/categories")
    public Response<ChangeBookCategoriesResponse> changeCategories(
            @PathVariable Long bookId,
            @Valid @RequestBody ChangeBookCategoriesRequest request
    ) {
        BookResult result = bookCategoriesUpdateApplication.changeBookCategories(request.toCommand(bookId));
        ChangeBookCategoriesResponse response = ChangeBookCategoriesResponse.from(result);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_CATEGORIES_CHANGE,
                response
        );
    }

    @PatchMapping("/{bookId}/damage")
    public Response<ChangeBookResponse> damage(
            @PathVariable Long bookId
    ) {
        Long savedId = bookUpdateApplication.damage(bookId);
        ChangeBookResponse response = new ChangeBookResponse(savedId);
        return Response.create(
                ResponseType.SUCCESS,
                ResponseMessage.BookResponseMessage.SUCCESS_BOOK_CHANGE,
                response
        );
    }
}
