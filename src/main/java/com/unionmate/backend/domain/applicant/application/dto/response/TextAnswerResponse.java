package com.unionmate.backend.domain.applicant.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;

import io.swagger.v3.oas.annotations.media.Schema;

public record TextAnswerResponse(
	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT)", example = "TEXT")
	ItemType itemType,

	@Schema(description = "항목 제목", example = "날짜 선택")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "해당 날짜 전까지 희망하는 날짜를 작성해주세요")
	String description,

	@Schema(description = "답변 최대 글자수", example = "700")
	Integer maxLength,

	@Schema(description = "질문 답변")
	String answer
) implements ApplicationAnswerResponse {

	public static TextAnswerResponse from(TextItem textItem) {
		return new TextAnswerResponse(
			ItemType.TEXT,
			textItem.getTitle(),
			textItem.getOrder(),
			textItem.getDescription(),
			textItem.getMaxLength(),
			textItem.getAnswer() == null ? null : textItem.getAnswer().answer()
		);
	}
}
