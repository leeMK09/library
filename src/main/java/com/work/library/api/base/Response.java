package com.work.library.api.base;

import com.work.library.application.exception.ErrorType;

/**
 * 공용 Response 객체
 *
 * @param type           응답 타입
 * @param message        응답 메시지
 * @param data           응답 데이터
 */
public record Response<T>(
    String type,
    String message,
    T data
) {
    public static <T> Response<T> create(
            ResponseType type,
            String message,
            T data
    ) {
        return new Response<T>(type.name(), message, data);
    }

    public static <T> Response<T> createErrorResponse(
            ErrorType type,
            String message
    ) {
        return new Response<T>(type.name(), message, null);
    }
}
