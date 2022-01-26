package com.vegana.vegana.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum AuthErrorCode implements ErrorCode {

    LOGIN_FAIL("AUTH_001", "로그인 오류"),
    TOKEN_FAIL("AUTH_002", "유효하지 않은 토큰", HttpStatus.UNAUTHORIZED),
    AUTHORITY_FAIL("AUTH_003", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    INVALID_JWT("AUTH_004", "잘못된 JWT 서명입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_JWT("AUTH_005", "만료된 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_JWT("AUTH_006", "지원되지 않는 JWT 토큰입니다.", HttpStatus.UNAUTHORIZED),
    ILLEGAL_ARGS_JWT("AUTH_007", "JWT 토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_CODE("AUTH_999", "유효하지 않은 코드입니다.")
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
