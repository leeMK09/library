package com.work.library.api.request;

import com.work.library.application.dto.command.RegisterBookCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegisterBookRequest {
    @NotBlank(message = "책 제목은 비어있을 수 없습니다.")
    private String title;
    @NotBlank(message = "책 저자는 비어있을 수 없습니다.")
    private String author;
    @NotNull(message = "카테고리 목록은 필수 입니다.")
    @NotEmpty(message = "카테고리 목록은 필수 입니다.")
    private List<Long> categoryIds;

    public RegisterBookCommand toCommand() {
        return new RegisterBookCommand(title, author, categoryIds);
    }

    public RegisterBookRequest(String title, String author, List<Long> categoryIds) {
        this.title = title;
        this.author = author;
        this.categoryIds = categoryIds;
    }
}
