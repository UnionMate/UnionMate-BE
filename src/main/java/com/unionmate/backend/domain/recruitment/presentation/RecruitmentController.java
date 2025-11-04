package com.unionmate.backend.domain.recruitment.presentation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.usecase.RecruitmentUseCase;
import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend/recruitment")
@RequiredArgsConstructor
public class RecruitmentController {
	private final RecruitmentUseCase recruitmentUseCase;

	@PostMapping
	@Operation(summary = "지원서 양식을 생성합니다.")
	public CommonResponse<Void> createRecruitment(
		@CurrentMemberId Long memberId, @Valid @RequestBody CreateRecruitmentRequest request
	) {
		recruitmentUseCase.createRecruitment(memberId, request);

		return CommonResponse.success(RecruitmentResponseCode.CREATE_RECRUITMENT);
	}

	@PatchMapping("/{recruitmentId}")
	@Operation(summary = "지원서 양식을 수정합니다.")
	public CommonResponse<Void> updateRecruitment(
		@CurrentMemberId Long memberId, @PathVariable Long recruitmentId,
		@Valid @RequestBody UpdateRecruitmentRequest request
	) {
		recruitmentUseCase.updateRecruitment(memberId, recruitmentId, request);

		return CommonResponse.success(RecruitmentResponseCode.UPDATE_RECRUITMENT);
	}

	@DeleteMapping("/{recruitmentId}")
	@Operation(summary = "지원서 양식을 삭제합니다.")
	public CommonResponse<Void> deleteRecruitment(@CurrentMemberId Long memberId, @PathVariable Long recruitmentId) {
		recruitmentUseCase.deleteRecruitment(memberId, recruitmentId);

		return CommonResponse.success(RecruitmentResponseCode.DELETE_RECRUITMENT);
	}

	@GetMapping("/{recruitmentId}")
	@Operation(summary = "지원서 양식을 조회합니다.")
	public CommonResponse<RecruitmentResponse> getRecruitment(
		@CurrentMemberId Long memberId, @PathVariable Long recruitmentId
	) {
		RecruitmentResponse recruitmentResponse = recruitmentUseCase.getRecruitmentForm(memberId, recruitmentId);

		return CommonResponse.success(RecruitmentResponseCode.GET_RECRUITMENT, recruitmentResponse);
	}
}
