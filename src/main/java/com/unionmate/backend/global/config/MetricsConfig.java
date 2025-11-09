package com.unionmate.backend.global.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Prometheus 메트릭 설정
 */
@Configuration
public class MetricsConfig {

    /**
     * 모든 메트릭에 공통 태그 추가
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .commonTags(
                        "application", "unionmate-backend",
                        "environment", "production"
                );
    }
}