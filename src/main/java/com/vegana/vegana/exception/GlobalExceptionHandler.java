package com.vegana.vegana.exception;

import com.vegana.vegana.exception.message.CommonErrorCode;
import com.vegana.vegana.exception.message.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 전역 에러처리 핸들러
 */
@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 어플리케이션 표준 에러 처리
     * @see VgnRuntimeException
     */
    @ExceptionHandler(VgnRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(VgnRuntimeException e) {
        return buildResponse(e, e.getErrorCode(), e.getDetail());
    }

    /**
     * 핸들링 되지 않은 에러 발생 대비
     * 여기로 호출된 에러는 메서드 추가하여 핸들링하도록 해야 한다.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Unhandled error occurred. Write handler method in GlobalExceptionHandler", e);
        return buildResponse(e, CommonErrorCode.UNHANDLED_ERROR, e.getMessage());
    }

    /**
     * request body 필드 검증 에러 처리
     * (MethodValidationInterceptor 에서 발생)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
        List<String> detail = e.getConstraintViolations()
                .stream()
                .map(cv -> makeConstraintMessage(cv.unwrap(ConstraintViolation.class)))
                .collect(Collectors.toList());

        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, detail);
    }

    /**
     * request body 필드 검증 에러 처리
     * (SpringValidatorAdapter 에서 발생)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        List<String> detail;

        if (e.getBindingResult().getFieldErrors().size() == 0) {
            detail = List.of(e.getMessage());
        } else {
            detail = e.getFieldErrors()
                    .stream()
                    .map(fe -> makeConstraintMessage(fe.unwrap(ConstraintViolation.class)))
                    .collect(Collectors.toList());
        }

        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, detail);
    }

    /**
     * request param 검증 에러 처리
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        List<String> detail = fieldErrors
                .stream()
                .filter(Objects::nonNull)
                .map(fe -> Optional.of(fe)
                        .filter(fee -> fee.contains(ConstraintViolation.class))
                        .map(fee -> makeConstraintMessage(fee.unwrap(ConstraintViolation.class)))
                        .orElse(fe.getDefaultMessage()))
                .collect(Collectors.toList());

        if (detail.isEmpty()) {
            return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, e.getMessage());
        }

        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, detail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleException(MissingServletRequestParameterException e) {
        String message = String.format("[%s] 필수값입니다.", e.getParameterName());
        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, message);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleException(TypeMismatchException e) {
        return buildResponse(e, CommonErrorCode.INVALID_REQUEST_PARAM, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpRequestMethodNotSupportedException e) {
        String requestMethod = e.getMethod();
        String supportedMethod = Optional.ofNullable(e.getSupportedHttpMethods())
                .map(Objects::toString)
                .orElse("");
        String message = String.format("지원하지않는 요청 방식(%s). 지원가능%s", requestMethod, supportedMethod);

        return buildResponse(e, CommonErrorCode.INVALID_REQUEST, message);
    }

    /**
     * ConstraintViolation 메시지 생성
     */
    private String makeConstraintMessage(ConstraintViolation violation) {
        String message = violation.getMessage();
        String[] fieldNameWithObject = violation.getPropertyPath().toString().split("[.]");
        String fieldName = fieldNameWithObject[fieldNameWithObject.length-1];
        return String.format("[%s] %s", fieldName, message);
    }

    private ResponseEntity<ErrorResponse> buildResponse(Throwable throwable, ErrorCode errorCode) {
        log.error("API_ERROR : {}", errorCode.getHttpStatus(), throwable);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }

    private ResponseEntity<ErrorResponse> buildResponse(Throwable throwable, ErrorCode errorCode, String message) {
        return buildResponse(throwable, errorCode, List.of(message));
    }

    private ResponseEntity<ErrorResponse> buildResponse(Throwable throwable, ErrorCode errorCode, List<String> detail) {
        log.error("API_ERROR : {}", errorCode.getHttpStatus(), throwable);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode, detail));
    }

    /**
     * 에러 응답 표준 객체
     */
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL) // Todo Null 값을 포함시킬지 논의 필요
    static class ErrorResponse {
        private final String code;
        private final String message;

        @Nullable
        private final List<String> detail;

        public ErrorResponse(ErrorCode errorCode) {
            this(errorCode, null);
        }

        public ErrorResponse(ErrorCode errorCode, List<String> detail) {
            this.code = errorCode.getCode();
            this.message = errorCode.getMessage();
            this.detail = detail;
        }
    }
}
