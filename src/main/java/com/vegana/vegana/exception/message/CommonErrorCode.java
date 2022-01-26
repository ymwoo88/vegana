package com.vegana.vegana.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorCode implements ErrorCode {

    UNHANDLED_ERROR("E_000", "오류 - 상세 오류 요청 필요", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST("E_001", "요청 오류"),
    INVALID_REQUEST_PARAM("E_002", "요청 파라미터 오류")
    ;

    String code;
    String message;
    HttpStatus httpStatus;

    CommonErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    CommonErrorCode(String code, String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }
}
