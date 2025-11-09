package com.unionmate.backend.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 실시간 모니터링용 경량 로거 (Loki용)
 * 핵심 정보만 간결하게 기록
 */
@Slf4j
@Component
public class MonitoringLogger {

    private static final Logger MONITORING = LoggerFactory.getLogger("MONITORING");

    private final ObjectMapper objectMapper;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public MonitoringLogger(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper.copy();
        this.objectMapper.getFactory().disable(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII);
    }

    /**
     * 간결한 비즈니스 이벤트 로그 (파라미터/응답 제외)
     */
    public void logEvent(String event, String category, boolean success, long durationMs, String errorType) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(formatter));
        logData.put("event", event);
        logData.put("category", category);
        logData.put("success", success);
        logData.put("duration_ms", durationMs);

        if (!success && errorType != null) {
            logData.put("error", errorType);
        }

        log(logData);
    }

    /**
     * API 호출 로그 (request body 포함)
     */
    public void logApi(String method, String path, int status, long durationMs, String errorType, String requestBody) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(formatter));
        logData.put("type", "API");
        logData.put("method", method);
        logData.put("path", path);
        logData.put("status", status);
        logData.put("duration_ms", durationMs);

        if (status >= 400 && errorType != null) {
            logData.put("error", errorType);
        }

        if (requestBody != null && !requestBody.isEmpty()) {
            logData.put("request_body", requestBody);
        }

        log(logData);
    }

    /**
     * 인증 이벤트 로그
     */
    public void logAuth(String event, String userId, boolean success, String reason) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(formatter));
        logData.put("type", "AUTH");
        logData.put("event", event);
        logData.put("userId", userId);
        logData.put("success", success);

        if (!success && reason != null) {
            logData.put("reason", reason);
        }

        log(logData);
    }

    /**
     * 에러 로그
     */
    public void logError(String event, String category, String errorType, String errorMessage, long durationMs) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(formatter));
        logData.put("level", "ERROR");
        logData.put("event", event);
        logData.put("category", category);
        logData.put("error", errorType);
        logData.put("message", errorMessage);
        logData.put("duration_ms", durationMs);

        log(logData);
    }

    /**
     * JSON 로그 기록
     */
    private void log(Map<String, Object> logData) {
        try {
            String jsonLog = objectMapper.writeValueAsString(logData);
            MONITORING.info(jsonLog);
        } catch (Exception e) {
            log.error("Failed to serialize monitoring log", e);
        }
    }
}