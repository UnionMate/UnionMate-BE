package com.unionmate.backend.domain.applicant.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateInterviewEvaluationRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateInterviewEvaluationRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.InterviewEvaluationResponse;
import com.unionmate.backend.domain.applicant.application.usecase.InterviewEvaluationUseCase;
import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend/applications/{applicationId}/evaluations")
@RequiredArgsConstructor
public class InterviewEvaluationController {

	private final InterviewEvaluationUseCase interviewEvaluationUseCase;

	@PostMapping
	@Operation(summary = "면접 평가를 생성합니다. (관리자 전용)")
	public CommonResponse<Void> createEvaluation(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @Valid @RequestBody CreateInterviewEvaluationRequest request) {
		interviewEvaluationUseCase.createEvaluation(memberId, applicationId, request);

		return CommonResponse.success(ApplicationResponseCode.CREATE_INTERVIEW_EVALUATION);
	}

	@GetMapping
	@Operation(summary = "면접 평가 목록을 조회합니다. (관리자 전용)")
	public CommonResponse<List<InterviewEvaluationResponse>> getEvaluations(@CurrentMemberId Long memberId, @PathVariable Long applicationId) {
		List<InterviewEvaluationResponse> responses = interviewEvaluationUseCase.getEvaluations(memberId, applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_INTERVIEW_EVALUATIONS, responses);
	}

	@PatchMapping("/{evaluationId}")
	@Operation(summary = "면접 평가를 수정합니다. (관리자 전용)")
	public CommonResponse<Void> updateEvaluation(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @PathVariable Long evaluationId, @Valid @RequestBody UpdateInterviewEvaluationRequest request) {
		interviewEvaluationUseCase.updateEvaluation(memberId, applicationId, evaluationId, request);

		return CommonResponse.success(ApplicationResponseCode.UPDATE_INTERVIEW_EVALUATION);
	}

	@DeleteMapping("/{evaluationId}")
	@Operation(summary = "면접 평가를 삭제합니다. (관리자 전용)")
	public CommonResponse<Void> deleteEvaluation(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @PathVariable Long evaluationId) {
		interviewEvaluationUseCase.deleteEvaluation(memberId, applicationId, evaluationId);

		return CommonResponse.success(ApplicationResponseCode.DELETE_INTERVIEW_EVALUATION);
	}
}
