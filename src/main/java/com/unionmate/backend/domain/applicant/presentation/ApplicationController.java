package com.unionmate.backend.domain.applicant.presentation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateApplicantRequest;
import com.unionmate.backend.domain.applicant.application.usecase.ApplicationUseCase;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.presentation.RecruitmentResponseCode;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend")
@RequiredArgsConstructor
public class ApplicationController {
	private final ApplicationUseCase applicationUseCase;

	@PostMapping("/{recruitmentId}/application")
	@Operation(summary = "지원서를 작성합니다.")
	public CommonResponse<Void> submitApplication(
		@PathVariable Long recruitmentId, @Valid @RequestBody CreateApplicantRequest createApplicantRequest) {
		applicationUseCase.submitApplication(recruitmentId, createApplicantRequest);

		return CommonResponse.success(ApplicationResponseCode.SUBMIT_APPLICATION);
	}
}
