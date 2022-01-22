package com.company.vegana.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode {

    TOKEN_FAIL("AUTH_001", "로그인 오류", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CODE("AUTH_002", "유효하지 않은 코드입니다.")
    ;

    final String code;
    final String message;
    final HttpStatus httpStatus;

    AuthErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    AuthErrorCode(String code, String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }
}
