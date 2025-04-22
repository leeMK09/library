package com.work.library.api.response;

/**
 * 저장 후 도서 응답
 *
 * @param id             저장한 도서의 ID
 */
public record RegisteredBookResponse(
        Long id
) {
}
