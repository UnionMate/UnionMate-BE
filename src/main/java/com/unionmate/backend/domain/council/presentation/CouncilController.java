package com.unionmate.backend.domain.council.presentation;

import static com.unionmate.backend.domain.council.presentation.CouncilResponseCode.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.council.application.dto.CreateCouncilRequest;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilResponse;
import com.unionmate.backend.domain.council.application.usecase.CouncilManageUsecase;
import com.unionmate.backend.global.response.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backend/councils")
public class CouncilController {
	private final CouncilManageUsecase councilManageUsecase;

	@PostMapping()
	public CommonResponse<CreateCouncilResponse> createCouncil(@RequestHeader long memberId, @RequestBody
		CreateCouncilRequest request) {
		CreateCouncilResponse response = councilManageUsecase.createCouncil(memberId, request);
		return CommonResponse.success(CREATE_COUNCIL, response);
	}
}
