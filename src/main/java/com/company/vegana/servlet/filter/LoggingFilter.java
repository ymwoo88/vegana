package com.company.vegana.servlet.filter;

import com.company.vegana.servlet.log.LogKey;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoggingFilter implements Filter {
    public static final String HEADER_ORIGIN = "Origin";
    public static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        addLogKey(request);
        addXForwardedFor(request);
        // LogKey를 사용하기때문에 addLogKey보다 뒤에 호출되어야 한다.
        addResponseHeader(request, response);
        chain.doFilter(req, res);
        removeXForwardedFor();
        removeLogKey();
    }

    private void addXForwardedFor(final HttpServletRequest request) {
        String ip = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (!StringUtils.hasLength(ip)) {
            ip = "";
        }
        MDC.put(HEADER_X_FORWARDED_FOR, ip);
    }

    private void addLogKey(final HttpServletRequest request) {
        LogKey.put(getLogKey(request));
    }

    private String getLogKey(final HttpServletRequest request) {
        String logKey = request.getHeader(LogKey.getLogKeyName());
        if (!StringUtils.hasLength(logKey)) {
            logKey = LogKey.createLogKey();
        }
        return logKey;
    }

    private void addResponseHeader(final HttpServletRequest request, final HttpServletResponse response) {
        final String origin = request.getHeader(HEADER_ORIGIN);
        if (log.isDebugEnabled()) {
            log.debug("origin => {}", origin);
        }

        // TODO: 기존 core에 있어서 호환성을 유지하기 위해서 옮기긴 했는데 왜 있는지 확인이 필요
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Expose-Headers", "Location,Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader(LogKey.getLogKeyName(), LogKey.get());
    }

    private void removeLogKey() {
        LogKey.remove();
    }

    private void removeXForwardedFor() {
        MDC.remove(HEADER_X_FORWARDED_FOR);
    }
}
