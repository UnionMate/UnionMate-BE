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
import com.unionmate.backend.domain.applicant.application.dto.request.GetMyApplicationsRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateApplicationRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateCommentRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.CommentResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetApplicationResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetMyApplicationsResponse;
import com.unionmate.backend.domain.applicant.application.usecase.ApplicationUseCase;
import com.unionmate.backend.domain.applicant.application.usecase.CommentUseCase;
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
		@PathVariable Long applicationId, @Valid @RequestBody UpdateApplicationRequest updateApplicationRequest
	) {
		applicationUseCase.updateApplication(applicationId, updateApplicationRequest);

		return CommonResponse.success(ApplicationResponseCode.UPDATE_APPLICATION);
	}

	@GetMapping("/mine")
	@Operation(summary = "자신이 작성한 지원서 목록을 조회합니다.")
	public CommonResponse<List<GetMyApplicationsResponse>> getMyApplications(
		@Valid GetMyApplicationsRequest getMyApplicationsRequest) {
		List<GetMyApplicationsResponse> myApplications = applicationUseCase.getMyApplications(getMyApplicationsRequest);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATIONS, myApplications);
	}

	@GetMapping("/{applicationId}")
	@Operation(summary = "특정 지원서를 조회합니다.")
	public CommonResponse<GetApplicationResponse> getApplication(@PathVariable Long applicationId) {
		GetApplicationResponse application = applicationUseCase.getApplication(applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATION, application);
	}

	@PostMapping("/{applicationId}/comments")
	@Operation(summary = "지원서 서류 평가 코멘트를 생성합니다. (관리자 전용)")
	public CommonResponse<Void> createComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @Valid @RequestBody CreateCommentRequest createCommentRequest) {
		commentUseCase.createComment(memberId, applicationId, createCommentRequest);

		return CommonResponse.success(ApplicationResponseCode.CREATE_COMMENT);
	}

	@PatchMapping("/{{applicationId}}/comments/{commentId}")
	@Operation(summary = "지원서 서류 평가 코멘트를 수정합니다. (관리자 전용)")
	public CommonResponse<Void> updateComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest updateCommentRequest) {
		commentUseCase.updateComment(memberId, applicationId, commentId, updateCommentRequest);

		return CommonResponse.success(ApplicationResponseCode.UPDATE_COMMENT);
	}

	@GetMapping("/{applicationId}}/comments")
	@Operation(summary = "지원서 서류 평가 코멘트 목록을 조회합니다. (임원 전용)")
	public CommonResponse<List<CommentResponse>> getComments(@CurrentMemberId Long memberId, @PathVariable Long applicationId) {
		List<CommentResponse> responses = commentUseCase.getComments(memberId, applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_COMMENTS, responses);
	}

	@DeleteMapping("/{{applicationId}}/comments/{commentId}")
	@Operation(summary = "지원서 서류 평가 코멘트를 삭제합니다. (임원 전용)")
	public CommonResponse<Void> deleteComment(@CurrentMemberId Long memberId, @PathVariable Long applicationId, @PathVariable Long commentId) {
		commentUseCase.deleteComment(memberId, applicationId, commentId);

		return CommonResponse.success(ApplicationResponseCode.DELETE_COMMENT);
	}
}
