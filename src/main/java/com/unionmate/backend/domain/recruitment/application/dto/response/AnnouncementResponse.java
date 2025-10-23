package com.unionmate.backend.domain.recruitment.application.dto.response;

import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;

import io.swagger.v3.oas.annotations.media.Schema;

public record AnnouncementResponse(
	@Schema(description = "공지 내용 id", example = "1")
	Long id,

	@Schema(description = "항목 타입 (TEXT, SELECT, CALENDAR, ANNOUNCEMENT)", example = "ANNOUNCEMENT")
	ItemType type,

	@Schema(description = "필수 입력 여부", example = "false")
	boolean required,

	@Schema(description = "항목 제목", example = "공지 사항")
	String title,

	@Schema(description = "표시 순서 (1부터 시작)", example = "1")
	Integer order,

	@Schema(description = "항목 설명 안내", example = "공지사항을 필수로 확인해주세요.")
	String description,

	@Schema(description = "공지 내용 안내", example = "면접 장소는 AI 공학관 402호입니다.")
	String announcement
) implements ItemResponse {

	public static AnnouncementResponse from(AnnouncementItem announcementItem, ItemType itemType) {
		return new AnnouncementResponse(
			announcementItem.getId(),
			itemType,
			announcementItem.getRequired(),
			announcementItem.getTitle(),
			announcementItem.getOrder(),
			announcementItem.getDescription(),
			announcementItem.getAnnouncement()
		);
	}
}
