package com.company.vegana.config.security;

import com.company.vegana.exception.VgnRuntimeException;
import com.company.vegana.exception.message.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        handlerExceptionResolver.resolveException(request, response, null, new VgnRuntimeException(AuthErrorCode.TOKEN_FAIL, authException.getMessage()));
    }
}
