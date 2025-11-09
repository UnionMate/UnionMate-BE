package com.unionmate.backend.global.config;

import com.unionmate.backend.global.auth.resolver.CurrentMemberIdArgumentResolver;
import com.unionmate.backend.global.interceptor.MetricsInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final CurrentMemberIdArgumentResolver currentMemberIdArgumentResolver;
  private final MetricsInterceptor metricsInterceptor;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(currentMemberIdArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 모든 API 요청에 대해 메트릭 수집
    registry.addInterceptor(metricsInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/actuator/**"); // Actuator 엔드포인트는 제외
  }
}
