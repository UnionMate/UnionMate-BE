package com.unionmate.backend.domain.recruitment.presentation;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.ToggleRecruitmentActivationRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.ToggleRecruitmentActivationResponse;
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

	@GetMapping("/{recruitmentId}")
	@Operation(summary = "지원서 양식을 조회합니다.")
	public CommonResponse<RecruitmentResponse> getRecruitment(@PathVariable Long recruitmentId) {
		RecruitmentResponse recruitmentResponse = recruitmentUseCase.getRecruitmentForm(recruitmentId);

		return CommonResponse.success(RecruitmentResponseCode.GET_RECRUITMENT, recruitmentResponse);
	}

	@PostMapping("/{recruitmentId}/activation")
	@Operation(
		summary = "학생회 모집을 게시합니다. (OFF→ON) (관리자 전용)",
		description = """
			- active=true는 endAt 기간 이후에는 허용되지 않음
			- 응답의 open은 isActive && 기간충족을 의미함 (함께 반환해주는 이유는 startAt 이전에도 미리 게시를 활성화할 수 있기 때문)
			"""
	)
	public CommonResponse<ToggleRecruitmentActivationResponse> toggleRecruitmentActivation(
		@CurrentMemberId Long memberId,
		@PathVariable Long recruitmentId,
		@Valid @RequestBody ToggleRecruitmentActivationRequest toggleRecruitmentActivationRequest
	) {
		LocalDateTime now = LocalDateTime.now();
		ToggleRecruitmentActivationResponse response = recruitmentUseCase.toggleRecruitmentActivation(
			memberId, recruitmentId, toggleRecruitmentActivationRequest, now
		);

		return CommonResponse.success(RecruitmentResponseCode.RECRUITMENT_TOGGLE_ACTIVATION, response);
	}
}
