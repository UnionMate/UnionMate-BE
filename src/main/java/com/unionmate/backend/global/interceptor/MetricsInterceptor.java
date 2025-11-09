package com.unionmate.backend.global.interceptor;

import com.unionmate.backend.global.metrics.ApiMetrics;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 모든 API 요청에 대해 자동으로 메트릭을 수집하는 인터셉터
 */
@Component
@RequiredArgsConstructor
public class MetricsInterceptor implements HandlerInterceptor {

    private final ApiMetrics apiMetrics;
    private static final String START_TIME_ATTRIBUTE = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 요청 시작 시간 기록
        request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        // 응답 완료 후 메트릭 수집
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime != null) {
            long duration = System.currentTimeMillis() - startTime;
            String uri = request.getRequestURI();
            String method = request.getMethod();
            String status = String.valueOf(response.getStatus());

            // API 요청 카운트
            apiMetrics.incrementApiRequest(uri, method, status);

            // 응답 시간 기록
            apiMetrics.recordApiResponseTime(uri, method, duration);

            // 에러 상태코드인 경우 추가 기록
            if (response.getStatus() >= 500) {
                apiMetrics.incrementBusinessError("HTTP_" + status);
            }
        }
    }
}