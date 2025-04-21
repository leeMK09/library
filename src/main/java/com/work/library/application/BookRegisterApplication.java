package com.work.library.application;

import com.work.library.application.dto.command.RegisterBookCommand;
import com.work.library.application.exception.BookApplicationException;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.CategoryQueryService;
import com.work.library.domain.book.Book;
import com.work.library.domain.category.Category;
import com.work.library.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookRegisterApplication {
    private final BookCommandService bookCommandService;

    private final CategoryQueryService categoryQueryService;

    public BookRegisterApplication(
            BookCommandService bookCommandService,
            CategoryQueryService categoryQueryService
    ) {
        this.bookCommandService = bookCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    public Long save(RegisterBookCommand command) {
        List<Category> categories = categoryQueryService.findAllByIdList(command.categoryIdList());

        if (categories.isEmpty() || hasMissingCategory(command.categoryIdList(), categories)) {
            log.error(
                    "생성할 카테고리가 존재하지 않습니다. requested categories = {}, found categories = {}",
                    command.categoryIdList().size(),
                    categories.size()
            );
            throw BookApplicationException.invalidParameter();
        }
        Long savedBookId = bookCommandService.save(command.toDomain(categories));
        return savedBookId;
    }

    private boolean hasMissingCategory(List<Long> idList, List<Category> categories) {
        return categories.size() != CollectionUtils.getDistinctCount(idList);
    }
}
