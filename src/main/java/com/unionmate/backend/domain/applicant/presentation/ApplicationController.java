package com.unionmate.backend.domain.applicant.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateApplicantRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.CreateCommentRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.DecisionRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.GetMyApplicationRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.SetInterviewScheduleRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateApplicationRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateCommentRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.CommentResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetApplicationAdminResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetApplicationResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetMyApplicationsResponse;
import com.unionmate.backend.domain.applicant.application.usecase.ApplicationDecisionUseCase;
import com.unionmate.backend.domain.applicant.application.usecase.ApplicationUseCase;
import com.unionmate.backend.domain.applicant.application.usecase.CommentUseCase;
import com.unionmate.backend.domain.applicant.application.usecase.InterviewScheduleUseCase;
import com.unionmate.backend.global.auth.annotation.CurrentMemberId;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend/applications")
@RequiredArgsConstructor
public class ApplicationController {
	private final ApplicationUseCase applicationUseCase;
	private final CommentUseCase commentUseCase;
	private final ApplicationDecisionUseCase applicationDecisionUseCase;
	private final InterviewScheduleUseCase interviewScheduleUseCase;

	@PostMapping("/{recruitmentId}")
	@Operation(summary = "지원서를 작성합니다.")
	public CommonResponse<Void> submitApplication(
		@PathVariable Long recruitmentId, @Valid @RequestBody CreateApplicantRequest createApplicantRequest) {
		LocalDateTime now = LocalDateTime.now();
		applicationUseCase.submitApplication(recruitmentId, createApplicantRequest, now);

		return CommonResponse.success(ApplicationResponseCode.SUBMIT_APPLICATION);
	}

	@PatchMapping("/{applicationId}")
	@Operation(summary = "지원서를 수정합니다.")
	public CommonResponse<Void> updateApplication(
		@PathVariable Long applicationId, @Valid @RequestBody UpdateApplicationRequest updateApplicationRequest,
		@Valid GetMyApplicationRequest getMyApplicationRequest
	) {
		applicationUseCase.updateApplication(applicationId, updateApplicationRequest, getMyApplicationRequest);

		return CommonResponse.success(ApplicationResponseCode.UPDATE_APPLICATION);
	}

	@GetMapping("/mine")
	@Operation(summary = "자신이 작성한 지원서 목록을 조회합니다.")
	public CommonResponse<List<GetMyApplicationsResponse>> getMyApplications(
		@Valid GetMyApplicationRequest getMyApplicationRequest) {
		List<GetMyApplicationsResponse> myApplications = applicationUseCase.getMyApplications(getMyApplicationRequest);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATIONS, myApplications);
	}

	@GetMapping("/{applicationId}/detail/admin")
	@Operation(
		summary = "특정 지원서를 상세 조회합니다. (관리자 전용)",
		description = """
			관리자 전용 단건 조회 API 입니다.
			- 같은 학생회 소속 검증을 수행합니다.
			- 답변 목록은 문항 order 오름차순으로 정렬됩니다.
			- 응답에는 모집 공고/지원자/면접/스테이지/답변 정보가 모두 포함됩니다.
			"""
	)
	public CommonResponse<GetApplicationAdminResponse> getApplicationForAdmin(
		@CurrentMemberId long memberId,
		@PathVariable long applicationId
	) {
		GetApplicationAdminResponse response = applicationUseCase.getApplicationForAdmin(memberId, applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_APPLICATION_DETAIL_FOR_ADMIN, response);
	}

	@GetMapping("/{applicationId}")
	@Operation(summary = "특정 지원서를 조회합니다.")
	public CommonResponse<GetApplicationResponse> getApplication(@PathVariable Long applicationId,
		@Valid GetMyApplicationRequest getMyApplicationRequest) {
		GetApplicationResponse application = applicationUseCase.getMyOneApplication(applicationId,
			getMyApplicationRequest);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATION, application);
	}

	@PostMapping("/{applicationId}/comments")
	@Operation(summary = "지원서 서류 평가 코멘트를 생성합니다. (관리자 전용)")
	public CommonResponse<Void> createComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId,
		@Valid @RequestBody CreateCommentRequest createCommentRequest) {
		commentUseCase.createComment(memberId, applicationId, createCommentRequest);

		return CommonResponse.success(ApplicationResponseCode.CREATE_COMMENT);
	}

	@PatchMapping("/{applicationId}/comments/{commentId}")
	@Operation(summary = "지원서 서류 평가 코멘트를 수정합니다. (관리자 전용)")
	public CommonResponse<Void> updateComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId,
		@PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
		commentUseCase.updateComment(memberId, applicationId, commentId, updateCommentRequest);

		return CommonResponse.success(ApplicationResponseCode.UPDATE_COMMENT);
	}

	@GetMapping("/{applicationId}/comments")
	@Operation(summary = "지원서 서류 평가 코멘트 목록을 조회합니다. (관리자 전용)")
	public CommonResponse<List<CommentResponse>> getComments(@CurrentMemberId Long memberId,
		@PathVariable Long applicationId) {
		List<CommentResponse> responses = commentUseCase.getComments(memberId, applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_COMMENTS, responses);
	}

	@DeleteMapping("/{applicationId}/comments/{commentId}")
	@Operation(summary = "지원서 서류 평가 코멘트를 삭제합니다. (관리자 전용)")
	public CommonResponse<Void> deleteComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId,
		@PathVariable Long commentId) {
		commentUseCase.deleteComment(memberId, applicationId, commentId);

		return CommonResponse.success(ApplicationResponseCode.DELETE_COMMENT);
	}

	@PatchMapping("/{applicationId}/document/decision")
	@Operation(summary = "서류 평가를 결과 확정합니다. (관리자 전용)")
	public CommonResponse<Void> decideOnDocument(
		@CurrentMemberId Long memberId,
		@PathVariable Long applicationId,
		@Valid @RequestBody DecisionRequest decisionRequest
	) {
		applicationDecisionUseCase.decideOnDocument(memberId, applicationId, decisionRequest);

		return CommonResponse.success(ApplicationResponseCode.DOCUMENT_DECISION);
	}

	@PatchMapping("/{applicationId}/interview/evaluation")
	@Operation(summary = "면접 평가 결과를 확정합니다. (관리자 전용)")
	public CommonResponse<Void> decideOnInterview(
		@CurrentMemberId Long memberId,
		@PathVariable Long applicationId,
		@Valid @RequestBody DecisionRequest decisionRequest
	) {
		applicationDecisionUseCase.decideOnInterview(memberId, applicationId, decisionRequest);

		return CommonResponse.success(ApplicationResponseCode.INTERVIEW_EVALUATION_DECISION);
	}

	@PatchMapping("/{applicationId}/interview/schedule")
	@Operation(summary = "면접 일정을 설정합니다. (관리자 전용)")
	public CommonResponse<Void> setInterviewSchedule(
		@CurrentMemberId Long memberId,
		@PathVariable Long applicationId,
		@Valid @RequestBody SetInterviewScheduleRequest setInterviewScheduleRequest
	) {
		interviewScheduleUseCase.setInterviewSchedule(memberId, applicationId, setInterviewScheduleRequest);

		return CommonResponse.success(ApplicationResponseCode.SET_INTERVIEW_SCHEDULE);
	}
}
