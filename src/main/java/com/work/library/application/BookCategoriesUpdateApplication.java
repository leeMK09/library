package com.work.library.application;

import com.work.library.application.dto.command.ChangeBookCategoriesCommand;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.BookQueryService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Book;
import com.work.library.domain.book.BookCategories;
import com.work.library.domain.book.event.BookCategoriesChangedEvent;
import com.work.library.domain.category.Category;
import com.work.library.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookCategoriesUpdateApplication {
    private final BookQueryService bookQueryService;

    private final BookCommandService bookCommandService;

    private final CategoryQueryService categoryQueryService;

    public BookCategoriesUpdateApplication(
            BookQueryService bookQueryService,
            BookCommandService bookCommandService,
            CategoryQueryService categoryQueryService
    ) {
        this.bookQueryService = bookQueryService;
        this.bookCommandService = bookCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    public void changeBookCategories(ChangeBookCategoriesCommand command) {
        Book book = bookQueryService.getById(command.bookId());
        List<Category> newCategories = categoryQueryService.findAllByIdList(command.newCategoryIdList());

        if (!CollectionUtils.isEqualsSize(newCategories, command.newCategoryIdList())) {
            log.error(
                    "요청 중 존재하지 않는 카테고리가 있습니다. requested categories : {}, found categories : {}",
                    command.newCategoryIdList().size(),
                    newCategories.size()
            );
            throw BookApplicationException.invalidParameterByCategory();
        }
        BookCategories newBookCategories = new BookCategories(newCategories);
        bookCommandService.changeBookCategories(book, newBookCategories);
    }
}
