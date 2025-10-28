package com.unionmate.backend.domain.applicant.application.dto.response;

import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectAnswerResponse(
	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT)", example = "SELECT")
	ItemType itemType,

	@Schema(description = "항목 제목", example = "날짜 선택")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "해당 날짜 전까지 희망하는 날짜를 작성해주세요")
	String description,

	@Schema(description = "중복 선택 가능 여부 ", example = "false")
	boolean multiple,

	@Schema(description = "답변한 선택지")
	List<Long> selectedOptionIds
) implements ApplicationAnswerResponse {

	public static SelectAnswerResponse from(SelectItem selectItem) {
		return new SelectAnswerResponse(
			ItemType.SELECT,
			selectItem.getTitle(),
			selectItem.getOrder(),
			selectItem.getDescription(),
			selectItem.isMultiple(),
			selectItem.getAnswer() == null ? List.of() : selectItem.getAnswer().answer()
		);
	}
}
