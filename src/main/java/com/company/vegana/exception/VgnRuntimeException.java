package com.company.vegana.exception;

import com.company.vegana.exception.message.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class VgnRuntimeException extends RuntimeException {

    private final ErrorCode errorCode;
    private final List<String> detail;

    public VgnRuntimeException(ErrorCode errorCode, List<String> detail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public VgnRuntimeException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = List.of(detail);
    }

    public VgnRuntimeException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;
    }
}