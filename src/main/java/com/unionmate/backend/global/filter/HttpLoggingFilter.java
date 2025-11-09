package com.unionmate.backend.global.filter;

import com.unionmate.backend.global.log.MonitoringLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * HTTP 요청/응답을 로깅하는 필터
 * - 모든 API 요청을 monitoring.log에 JSON 형식으로 기록
 * - 400번대, 500번대 에러 모두 포함
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final MonitoringLogger monitoringLogger;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // ContentCachingWrapper로 감싸서 바디를 여러 번 읽을 수 있게 함
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // API 로그 기록 (monitoring.log)
            logApiCall(wrappedRequest, wrappedResponse, duration);

            // 응답 바디를 실제 응답으로 복사 (중요!)
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logApiCall(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration
    ) {
        try {
            String method = request.getMethod();
            String path = getEndpointPattern(request.getRequestURI());
            int status = response.getStatus();
            String errorType = status >= 400 ? getErrorType(status) : null;
            String requestBody = getRequestBody(request);

            // MonitoringLogger를 사용하여 JSON 로그 기록
            monitoringLogger.logApi(method, path, status, duration, errorType, requestBody);

        } catch (Exception e) {
            log.error("Failed to log API call", e);
        }
    }

    /**
     * URL에서 패턴 추출 (경로 변수를 일반화)
     * 예: /api/recruitments/123 -> /api/recruitments/{id}
     */
    private String getEndpointPattern(String uri) {
        // 숫자로만 이루어진 경로 파라미터를 {id}로 변환
        return uri.replaceAll("/\\d+", "/{id}");
    }

    /**
     * HTTP 상태 코드에 따른 에러 타입 반환
     */
    private String getErrorType(int status) {
        if (status >= 500) {
            return "SERVER_ERROR_" + status;
        } else if (status >= 400) {
            return "CLIENT_ERROR_" + status;
        }
        return null;
    }

    /**
     * Request Body 읽기 (최대 10KB)
     */
    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }

        int maxLength = 10000; // 10KB
        int length = Math.min(content.length, maxLength);

        try {
            String body = new String(content, 0, length, java.nio.charset.StandardCharsets.UTF_8);
            if (content.length > maxLength) {
                body += " ...(truncated)";
            }
            return body;
        } catch (Exception e) {
            return "[Error reading request body]";
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        // Actuator, Swagger 등은 로깅에서 제외
        return path.startsWith("/actuator")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/favicon.ico");
    }
}