package com.unionmate.backend.domain.applicant.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unionmate.backend.domain.applicant.domain.entity.Comment;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentResponse(
	@Schema(description = "평가 ID", example = "101")
	Long commentId,

	@Schema(description = "평가를 작성한 임원(CouncilManager) 식별자", example = "42")
	Long councilManagerId,

	@Schema(description = "평가 작성한 임원의 이름(멤버 이름)", example = "이지훈")
	String councilManagerName,

	@Schema(description = "평가 내용(최대 250자)", example = "면접 시 발표 경험이 좋아 보였습니다.")
	String content,

	@Schema(description = "평가 생성 시각", example = "2025-10-20T15:30:00")
	LocalDateTime createdAt,

	@Schema(description = "평가 수정 시각", example = "2025-10-21T10:05:12")
	LocalDateTime updatedAt
) {
	public static CommentResponse from(Comment comment) {
		String councilManagerName = comment.getCouncilManager().getMember().getName();
		return new CommentResponse(
			comment.getId(),
			comment.getCouncilManager().getId(),
			councilManagerName,
			comment.getContent(),
			comment.getCreatedAt(),
			comment.getUpdatedAt()
		);
	}

	public static List<CommentResponse> fromList(List<Comment> comments) {
		return comments.stream().map(CommentResponse::from).toList();
	}

	public static CommentResponse of(Long commentId,
		Long councilManagerId,
		String councilManagerName,
		String content,
		LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		return new CommentResponse(commentId, councilManagerId, councilManagerName, content, createdAt, updatedAt);
	}
}