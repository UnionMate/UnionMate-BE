package com.unionmate.backend.domain.applicant.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateApplicantRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.GetMyApplicationsRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.GetApplicationResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetMyApplicationsResponse;
import com.unionmate.backend.domain.applicant.application.usecase.ApplicationUseCase;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend/applications")
@RequiredArgsConstructor
public class ApplicationController {
	private final ApplicationUseCase applicationUseCase;

	@PostMapping("/{recruitmentId}")
	@Operation(summary = "지원서를 작성합니다.")
	public CommonResponse<Void> submitApplication(
		@PathVariable Long recruitmentId, @Valid @RequestBody CreateApplicantRequest createApplicantRequest) {
		applicationUseCase.submitApplication(recruitmentId, createApplicantRequest);

		return CommonResponse.success(ApplicationResponseCode.SUBMIT_APPLICATION);
	}

	@GetMapping("/{name}/{email}")
	@Operation(summary = "자신이 작성한 지원서 목록을 조회합니다.")
	public CommonResponse<List<GetMyApplicationsResponse>> getMyApplications(
		@RequestBody GetMyApplicationsRequest getMyApplicationsRequest) {
		List<GetMyApplicationsResponse> myApplications = applicationUseCase.getMyApplications(getMyApplicationsRequest);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATIONS, myApplications);
	}

	@GetMapping("/{applicationId}")
	@Operation(summary = "특정 지원서를 조회합니다.")
	public CommonResponse<GetApplicationResponse> getApplication(@PathVariable Long applicationId) {
		GetApplicationResponse application = applicationUseCase.getApplication(applicationId);

		return CommonResponse.success(ApplicationResponseCode.GET_MY_APPLICATION, application);
	}
}
