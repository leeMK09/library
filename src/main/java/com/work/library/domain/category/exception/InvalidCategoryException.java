package com.work.library.domain.category.exception;

import com.work.library.domain.ErrorMessage;

public class InvalidCategoryException extends RuntimeException {
    public InvalidCategoryException(String message) {
        super(message);
    }

    public static InvalidCategoryException blankName() {
        return new InvalidCategoryException(ErrorMessage.CATEGORY_NAME_BLANK);
    }
}
