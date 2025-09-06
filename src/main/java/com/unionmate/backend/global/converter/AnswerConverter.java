package com.unionmate.backend.global.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unionmate.backend.domain.applicant.entity.column.Answer;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class AnswerConverter implements AttributeConverter<Answer<?>, String> {

  private static final ObjectMapper OBJECT_MAPPER =
      JsonMapper.builder()
          .addModule(new JavaTimeModule())
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .build();

  @Override
  public String convertToDatabaseColumn(Answer<?> attribute) {
    if (attribute == null) {
      return null;
    }
    try {
      return OBJECT_MAPPER.writeValueAsString(attribute);
    } catch (JsonProcessingException e) {
      // TODO: 예외 처리 로직 완성되면 예외 처리
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Answer<?> convertToEntityAttribute(String dbData) {
    if (dbData == null || dbData.isEmpty()) {
      return null;
    }
    try {
      return OBJECT_MAPPER.readValue(dbData, new TypeReference<Answer<?>>() {
      });
    } catch (Exception e) {
      // TODO: 예외 처리 로직 완성되면 예외 처리
      throw new IllegalArgumentException(e);
    }
  }
}
