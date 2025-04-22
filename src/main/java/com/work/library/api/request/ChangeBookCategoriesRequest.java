package com.work.library.api.request;

import com.work.library.application.dto.command.ChangeBookCategoriesCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChangeBookCategoriesRequest {
    @NotNull(message = "카테고리 목록은 필수 입니다.")
    @NotEmpty(message = "카테고리 목록은 필수 입니다.")
    private List<Long> ids;

    public ChangeBookCategoriesCommand toCommand(Long bookId) {
        return new ChangeBookCategoriesCommand(bookId, ids);
    }

    public ChangeBookCategoriesRequest(List<Long> ids) {
        this.ids = ids;
    }
}
