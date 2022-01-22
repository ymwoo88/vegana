package com.company.vegana.servlet.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class ApiResponse<T> extends TraceableResponse {
    private T data;

    public static <T> ApiResponse<T> of(String logKey) {
        return ApiResponse.<T>builder()
                .logKey(logKey)
                .build();
    }
}
