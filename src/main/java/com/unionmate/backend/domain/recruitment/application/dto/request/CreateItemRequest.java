package com.unionmate.backend.domain.recruitment.application.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateItemRequest(
	@NotNull(message = "항목의 타입 지정은 필수입니다.")
	ItemType type,
	@NotNull(message = "필수 작성 항목 여부 지정은 필수입니다.")
	Boolean required,
	@NotBlank(message = "항목의 이름은 필수입니다.")
	String title,
	@NotNull(message = "항목의 순서 지정은 필수입니다.")
	Integer order,
	@NotBlank(message = "해당 항목에 대한 설명은 필수입니다.")
	String description,

	//Select용
	Boolean multiple,
	List<SelectOptionRequest> options,

	//text용
	String text,
	Integer maxLength,

	//calendar용
	LocalDate date,

	//announcement용
	String announcement
) {
}
