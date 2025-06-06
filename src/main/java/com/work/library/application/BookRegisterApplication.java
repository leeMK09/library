package com.work.library.application;

import com.work.library.application.dto.command.RegisterBookCommand;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Book;
import com.work.library.domain.category.Category;
import com.work.library.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookRegisterApplication {
    private final BookQueryService bookQueryService;

    private final BookCommandService bookCommandService;

    private final CategoryQueryService categoryQueryService;

    public BookRegisterApplication(
            BookQueryService bookQueryService,
            BookCommandService bookCommandService,
            CategoryQueryService categoryQueryService
    ) {
        this.bookQueryService = bookQueryService;
        this.bookCommandService = bookCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    public Long save(RegisterBookCommand command) {
        List<Category> categories = categoryQueryService.findAllByIds(command.categoryIdList());

        if (categories.isEmpty() || hasMissingCategory(command.categoryIdList(), categories)) {
            log.error(
                    "[BookRegisterApplication] 생성할 카테고리가 존재하지 않습니다. requested categories = {}, found categories = {}",
                    command.categoryIdList().size(),
                    categories.size()
            );
            throw BookApplicationException.notFoundCategories();
        }
        Optional<Book> existBook = bookQueryService.searchByTitleAndAuthor(command.title(), command.author());

        if (existBook.isPresent()) {
            log.error(
                    "[BookRegisterApplication] 요청한 지은이와 책 제목이 모두 일치하는 책이 이미 등록되어있습니다. requested book id = {}",
                    existBook.get().getId()
            );
            throw BookApplicationException.duplicatedBooks();
        }

        Long savedBookId = bookCommandService.save(command.toDomain(categories));

        log.info("[BookRegisterApplication] 도서 등록 성공");
        return savedBookId;
    }

    private boolean hasMissingCategory(List<Long> idList, List<Category> categories) {
        return !CollectionUtils.isEqualsSize(categories, idList);
    }
}
