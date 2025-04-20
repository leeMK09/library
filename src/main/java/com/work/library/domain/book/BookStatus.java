package com.work.library.domain.book;

public enum BookStatus {
    AVAILABLE("사용가능"),
    DAMAGED("훼손");

    private final String description;

    BookStatus(String description) {
        this.description = description;
    }
}
