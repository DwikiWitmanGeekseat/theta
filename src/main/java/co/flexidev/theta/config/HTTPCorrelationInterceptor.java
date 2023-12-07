package co.flexidev.theta.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class HTTPCorrelationInterceptor implements HandlerInterceptor {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String CORRELATION_ID_LOG = "correlationId";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String cor_id = UUID.randomUUID().toString();
        MDC.put(CORRELATION_ID_LOG, cor_id);
        response.setHeader(CORRELATION_ID_HEADER, cor_id);
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Object handler,
                                final Exception ex) {
        MDC.remove(CORRELATION_ID_LOG);
    }

}