package com.work.library.application.dto.command;

import java.util.List;

/**
 * 도서 등록을 위한 커맨드 객체.
 *
 * @param id              도서 ID
 * @param title           도서 제목
 * @param author          도서 저자 이름
 * @param categoryIdList  도서가 속한 카테고리 ID 목록
 */
public record RegisterBookCommand(
        Long id,
        String title,
        String author,
        List<Long> categoryIdList
) {
}
