package com.unionmate.backend.global.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * API 메트릭 수집
 */
@Component
@RequiredArgsConstructor
public class ApiMetrics {

    private final MeterRegistry meterRegistry;

    /**
     * API 요청 카운터
     */
    public void incrementApiRequest(String endpoint, String method, String status) {
        Counter.builder("api.requests.total")
                .tag("endpoint", endpoint)
                .tag("method", method)
                .tag("status", status)
                .description("Total API requests")
                .register(meterRegistry)
                .increment();
    }

    /**
     * API 응답 시간 기록
     */
    public void recordApiResponseTime(String endpoint, String method, long duration) {
        Timer.builder("api.response.time")
                .tag("endpoint", endpoint)
                .tag("method", method)
                .description("API response time")
                .register(meterRegistry)
                .record(duration, TimeUnit.MILLISECONDS);
    }

    /**
     * 비즈니스 에러 카운터
     */
    public void incrementBusinessError(String errorType) {
        Counter.builder("business.errors.total")
                .tag("error_type", errorType)
                .description("Total business errors")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 인증 실패 카운터
     */
    public void incrementAuthenticationFailure(String reason) {
        Counter.builder("auth.failures.total")
                .tag("reason", reason)
                .description("Authentication failures")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 데이터베이스 쿼리 카운터
     */
    public void incrementDatabaseQuery(String queryType, String table) {
        Counter.builder("database.queries.total")
                .tag("query_type", queryType)
                .tag("table", table)
                .description("Database queries")
                .register(meterRegistry)
                .increment();
    }

    /**
     * 활성 사용자 수 (게이지)
     */
    public void setActiveUsers(int count) {
        meterRegistry.gauge("users.active", count);
    }
}