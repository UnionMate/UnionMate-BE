package com.unionmate.backend.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Value("${cert-server-uri}")
  private String certServerUri;

  @Bean(name = "certRestClient")
  public RestClient certRestClient() {
    return RestClient.builder().baseUrl(certServerUri).build();
  }
}
