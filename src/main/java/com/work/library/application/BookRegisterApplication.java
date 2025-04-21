package com.work.library.application;

import com.work.library.application.dto.command.RegisterBookCommand;
import com.work.library.application.service.BookCommandService;
import com.work.library.application.service.CategoryQueryService;
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

    public void save(RegisterBookCommand command) {
        List<Category> categories = categoryQueryService.findAllByIdList(command.categoryIdList());

        if (categories.isEmpty() || hasMissingCategory(command.categoryIdList(), categories)) {
        }
//        bookCommandService.save()
    }

    private boolean hasMissingCategory(List<Long> idList, List<Category> categories) {
        return categories.size() != CollectionUtils.getDistinctCount(idList);
    }
}
