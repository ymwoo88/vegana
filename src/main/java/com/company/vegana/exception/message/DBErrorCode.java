package com.company.vegana.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DBErrorCode implements ErrorCode {

    INSERT_ERROR("DB_001", "DB 데이터 등록 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    SELECT_ERROR("DB_002", "DB 데이터 조회 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    SELECT_LIST_ERROR("DB_002_1", "DB 데이터 리스트 조회 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    SELECT_PAGE_ERROR("DB_002_2", "DB 데이터 페이징 조회 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_ERROR("DB_003", "DB 데이터 수정 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_ERROR("DB_004", "DB 데이터 삭제 실패.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    final String code;
    final String message;
    final HttpStatus httpStatus;

    DBErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
