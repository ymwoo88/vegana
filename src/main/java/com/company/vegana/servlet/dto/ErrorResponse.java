package com.company.vegana.servlet.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Data
public class ErrorResponse extends TraceableResponse {
    private List<ErrorCause> errors;

    // 예제코드니까 적당히 ... shiry
    public static ErrorResponse of(String message, String logKey) {
        return ErrorResponse.builder()
                .errors(List.of(ErrorCause.builder().message(message).build()))
                .logKey(logKey)
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ErrorCause {
        private String message;
        private String detail;
    }
}
