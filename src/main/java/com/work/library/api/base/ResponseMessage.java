package com.work.library.api.base;

public class ResponseMessage {
    public static class BookResponseMessage {
        public static String SUCCESS_BOOK_READ = "도서 조회에 성공하였습니다.";
        public static String SUCCESS_BOOK_SAVE = "도서 등록에 성공하였습니다.";
    }

    public static class CategoryResponseMessage {
        public static String SUCCESS_CATEGORIES_READ = "카테고리 목록 조회에 성공하였습니다.";
    }
}
