package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateItemRequest(
	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT", example = "TEXT")
	@NotNull(message = "항목의 타입 지정은 필수입니다.")
	ItemType type,

	@Schema(description = "필수 입력 여부", example = "true")
	@NotNull(message = "필수 작성 항목 여부 지정은 필수입니다.")
	Boolean required,

	@Schema(description = "항목 제목", example = "지원동기")
	@NotBlank(message = "항목의 이름은 필수입니다.")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	@NotNull(message = "항목의 순서 지정은 필수입니다.")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "지원동기에 대해서 간략히 설명해주세요 (500자)")
	@NotBlank(message = "해당 항목에 대한 설명은 필수입니다.")
	String description,

	//Select용
	@Schema(description = "단일/복수 선택 여부", example = "true")
	Boolean multiple,

	List<SelectOptionRequest> options,

	//text용
	String text,

	@Schema(description = "답변 최대 글자 수", example = "700")
	Integer maxLength,

	//calendar용
	@Schema(description = "날짜 지정", example = "2001-06-15")
	LocalDate date,

	//announcement용
	@Schema(description = "공지 내용 안내", example = "면접 장소는 AI 공학관 402호입니다.")
	String announcement
) {
}
