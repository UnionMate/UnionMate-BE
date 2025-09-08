package com.unionmate.backend.global.apis.advice;

import com.unionmate.backend.global.apis.ApiData;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ApiDataAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {

    if (!(body instanceof ApiData<?> apiResult)) {
      return body;
    }

    response.setStatusCode(apiResult.getHttpStatus());
    response.getHeaders().addAll(buildHeaders(apiResult));

    if (apiResult.getContentType() == MediaType.APPLICATION_JSON) {
      return apiResult;
    } else {
      return apiResult.getData();
    }
  }

  private HttpHeaders buildHeaders(ApiData<?> apiResult) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(apiResult.getContentType());
    apiResult.getHeaders().forEach(header -> headers.add(header.name(), header.value()));

    return headers;
  }
}
