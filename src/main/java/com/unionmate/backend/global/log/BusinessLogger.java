package com.unionmate.backend.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 비즈니스 로그를 구조화된 JSON 형태로 기록하는 유틸리티
 */
@Slf4j
@Component
public class BusinessLogger {

    private static final Logger USER_ACTIVITY = LoggerFactory.getLogger("BUSINESS.USER_ACTIVITY");
    private static final Logger RECRUITMENT = LoggerFactory.getLogger("BUSINESS.RECRUITMENT");
    private static final Logger APPLICATION_FLOW = LoggerFactory.getLogger("BUSINESS.APPLICATION_FLOW");
    private static final Logger INTERVIEW = LoggerFactory.getLogger("BUSINESS.INTERVIEW");
    private static final Logger API_PERFORMANCE = LoggerFactory.getLogger("PERFORMANCE.API");
    private static final Logger SLOW_QUERY = LoggerFactory.getLogger("PERFORMANCE.SLOW_QUERY");
    private static final Logger AUTH = LoggerFactory.getLogger("SECURITY.AUTH");
    private static final Logger SUSPICIOUS = LoggerFactory.getLogger("SECURITY.SUSPICIOUS");

    private final ObjectMapper objectMapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public BusinessLogger(ObjectMapper objectMapper) {
        // UTF-8 한글 처리를 위한 ObjectMapper 설정
        this.objectMapper = objectMapper.copy();
        // ASCII가 아닌 문자를 escape하지 않도록 설정
        this.objectMapper.getFactory().disable(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII);
    }

    /**
     * 사용자 활동 로그
     */
    public void logUserActivity(String event, String userId, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("userId", userId);
        logData.putAll(details);
        log(USER_ACTIVITY, logData);
    }

    /**
     * 모집 관련 로그
     */
    public void logRecruitment(String event, String recruitmentId, String councilId, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("recruitmentId", recruitmentId);
        logData.put("councilId", councilId);
        logData.putAll(details);
        log(RECRUITMENT, logData);
    }

    /**
     * 지원서 흐름 로그
     */
    public void logApplicationFlow(String event, String applicationId, String recruitmentId, String stage, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("applicationId", applicationId);
        logData.put("recruitmentId", recruitmentId);
        logData.put("stage", stage);
        logData.putAll(details);
        log(APPLICATION_FLOW, logData);
    }

    /**
     * 면접 평가 로그
     */
    public void logInterview(String event, String evaluationId, String applicationId, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("evaluationId", evaluationId);
        logData.put("applicationId", applicationId);
        logData.putAll(details);
        log(INTERVIEW, logData);
    }

    /**
     * API 성능 로그 (request/response body 포함)
     */
    public void logApiPerformance(String method, String endpoint, int status, long durationMs,
                                   String requestBody, String responseBody, Map<String, Object> additionalInfo) {
        Map<String, Object> logData = createBaseLog("API_CALL");
        logData.put("method", method);
        logData.put("endpoint", endpoint);
        logData.put("status", status);
        logData.put("duration_ms", durationMs);
        logData.put("requestBody", requestBody);
        logData.put("responseBody", responseBody);

        if (additionalInfo != null) {
            logData.putAll(additionalInfo);
        }

        // 느린 API는 별도 로그에도 기록 (500ms 기준)
        if (durationMs > 500) {
            logData.put("slowQuery", true);
            log(SLOW_QUERY, logData);
        }

        log(API_PERFORMANCE, logData);
    }

    /**
     * 인증/인가 로그
     */
    public void logAuth(String event, String userId, boolean success, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("userId", userId);
        logData.put("success", success);
        logData.putAll(details);
        log(AUTH, logData);
    }

    /**
     * 의심스러운 활동 로그
     */
    public void logSuspicious(String event, String userId, String reason, Map<String, Object> details) {
        Map<String, Object> logData = createBaseLog(event);
        logData.put("userId", userId);
        logData.put("reason", reason);
        logData.put("severity", "HIGH");
        logData.putAll(details);
        log(SUSPICIOUS, logData);
    }

    /**
     * 기본 로그 구조 생성
     */
    private Map<String, Object> createBaseLog(String event) {
        Map<String, Object> logData = new HashMap<>();
        logData.put("timestamp", LocalDateTime.now().format(formatter));
        logData.put("event", event);
        return logData;
    }

    /**
     * JSON 형태로 로그 기록
     */
    private void log(Logger logger, Map<String, Object> logData) {
        try {
            String jsonLog = objectMapper.writeValueAsString(logData);
            logger.info(jsonLog);
        } catch (Exception e) {
            logger.error("Failed to serialize log data", e);
        }
    }
}