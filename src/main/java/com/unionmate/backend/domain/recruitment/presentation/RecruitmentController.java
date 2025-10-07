package com.unionmate.backend.domain.recruitment.presentation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.usecase.RecruitmentUseCase;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/backend/recruitment")
@RequiredArgsConstructor
public class RecruitmentController {
	private final RecruitmentUseCase recruitmentUseCase;

	@PostMapping("/create")
	@Operation(summary = "지원서 양식 생성에 성공했습니다.")
	public CommonResponse<Void> createRecruitment(@Valid @RequestBody CreateRecruitmentRequest request) {
		recruitmentUseCase.createRecruitment(request);

		return CommonResponse.success(RecruitmentResponseCode.CREATE_RECRUITMENT);
	}
}
