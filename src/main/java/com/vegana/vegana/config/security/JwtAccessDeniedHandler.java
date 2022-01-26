package com.vegana.vegana.config.security;

import com.vegana.vegana.exception.VgnRuntimeException;
import com.vegana.vegana.exception.message.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        // 필요한 권한이 없이 접근하려 할때 403
        handlerExceptionResolver.resolveException(request, response, null, new VgnRuntimeException(AuthErrorCode.AUTHORITY_FAIL, accessDeniedException.getMessage()));
    }
}
