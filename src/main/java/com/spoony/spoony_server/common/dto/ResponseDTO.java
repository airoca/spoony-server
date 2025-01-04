package com.spoony.spoony_server.common.dto;

import com.spoony.spoony_server.common.message.DefaultErrorMessage;

public record ResponseDTO<T> (
        boolean success,
        T data,
        ErrorDTO error
) {
    public static <T> ResponseDTO<T> success(final T data) {
        return new ResponseDTO<>(true, data, null);
    }

    public static <T> ResponseDTO<T> fail(DefaultErrorMessage error) {
        return new ResponseDTO<>(false, null, ErrorDTO.of(error.getMessage()));
    }
}