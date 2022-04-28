package com.vegana.vegana.config.servlet;

import com.vegana.vegana.servlet.dto.VgnApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class VgnResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(final MethodParameter returnType, final Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return new VgnApiResponse(body);
    }
}