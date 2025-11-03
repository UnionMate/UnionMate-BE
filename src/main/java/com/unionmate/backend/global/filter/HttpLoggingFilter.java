package com.unionmate.backend.global.filter;

import com.unionmate.backend.global.log.BusinessLogger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpLoggingFilter extends OncePerRequestFilter {

    private final BusinessLogger businessLogger;
    private static final int MAX_PAYLOAD_LENGTH = 10000; // 최대 로그 길이 (10KB)

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

            // 요청/응답 로깅
            logRequestAndResponse(wrappedRequest, wrappedResponse, duration);

            // 응답 바디를 실제 응답으로 복사 (중요!)
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequestAndResponse(
            ContentCachingRequestWrapper request,
            ContentCachingResponseWrapper response,
            long duration
    ) {
        int status = response.getStatus();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();

        // 요청 정보
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n========== HTTP Request/Response ==========\n");
        logMessage.append(String.format("[%s] %s", method, uri));

        if (queryString != null) {
            logMessage.append("?").append(queryString);
        }
        logMessage.append("\n");
        logMessage.append(String.format("Status: %d | Duration: %dms\n", status, duration));

        // 요청 헤더
        logMessage.append("--- Request Headers ---\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // 민감한 헤더는 마스킹
            if (headerName.equalsIgnoreCase("Authorization")) {
                logMessage.append(String.format("%s: ***MASKED***\n", headerName));
            } else {
                logMessage.append(String.format("%s: %s\n", headerName, request.getHeader(headerName)));
            }
        }

        // 요청 바디
        String requestBody = getRequestBody(request);
        if (!requestBody.isEmpty()) {
            logMessage.append("--- Request Body ---\n");
            logMessage.append(requestBody).append("\n");
        }

        // 응답 바디
        String responseBody = getResponseBody(response);
        if (!responseBody.isEmpty()) {
            logMessage.append("--- Response Body ---\n");
            logMessage.append(responseBody).append("\n");
        }

        logMessage.append("==========================================");

        // 상태 코드별 로그 레벨 분기
        if (status >= 500) {
            // 500번대: ERROR
            log.error(logMessage.toString());
        } else if (status >= 400) {
            // 400번대: WARN
            log.warn(logMessage.toString());
        } else if (status >= 200 && status < 300) {
            // 200번대: INFO
            log.info(logMessage.toString());
        } else {
            // 기타: INFO
            log.info(logMessage.toString());
        }

        // 비즈니스 로그: API 성능 로깅 (request/response body 포함)
        try {
            String endpoint = getEndpointPattern(uri);

            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("queryString", queryString);
            additionalInfo.put("userAgent", request.getHeader("User-Agent"));

            businessLogger.logApiPerformance(
                    method,
                    endpoint,
                    status,
                    duration,
                    requestBody.isEmpty() ? "N/A" : requestBody,
                    responseBody.isEmpty() ? "N/A" : responseBody,
                    additionalInfo
            );
        } catch (Exception e) {
            log.error("Failed to log API performance", e);
        }
    }

    /**
     * URL에서 패턴 추출 (경로 변수를 일반화)
     * 예: /api/applications/123 -> /api/applications/{id}
     */
    private String getEndpointPattern(String uri) {
        // 숫자로만 이루어진 경로 파라미터를 {id}로 변환
        return uri.replaceAll("/\\d+", "/{id}");
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        // ContentCachingRequestWrapper는 요청을 읽은 후에만 캐시됨
        // 따라서 요청 바디가 비어있으면 빈 문자열 반환
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }

        int length = Math.min(content.length, MAX_PAYLOAD_LENGTH);
        try {
            // 명시적으로 UTF-8로 디코딩
            String body = new String(content, 0, length, java.nio.charset.StandardCharsets.UTF_8);

            if (content.length > MAX_PAYLOAD_LENGTH) {
                body += "\n... (truncated)";
            }

            return body;
        } catch (Exception e) {
            return "[Error reading request body]";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }

        int length = Math.min(content.length, MAX_PAYLOAD_LENGTH);
        try {
            // 명시적으로 UTF-8로 디코딩
            String body = new String(content, 0, length, java.nio.charset.StandardCharsets.UTF_8);

            if (content.length > MAX_PAYLOAD_LENGTH) {
                body += "\n... (truncated)";
            }

            return body;
        } catch (Exception e) {
            return "[Error reading response body]";
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