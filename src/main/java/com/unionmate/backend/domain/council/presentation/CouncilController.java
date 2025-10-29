package com.unionmate.backend.domain.council.presentation;

import static com.unionmate.backend.domain.council.presentation.CouncilResponseCode.*;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unionmate.backend.domain.council.application.dto.CouncilMemberResponse;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilRequest;
import com.unionmate.backend.domain.council.application.dto.CreateCouncilResponse;
import com.unionmate.backend.domain.council.application.dto.DelegateViceRequest;
import com.unionmate.backend.domain.council.application.dto.UpdateCouncilNameRequest;
import com.unionmate.backend.domain.council.application.dto.UpdateCouncilNameResponse;
import com.unionmate.backend.domain.council.application.dto.UpdateInvitationCodeRequest;
import com.unionmate.backend.domain.council.application.dto.UpdateInvitationCodeResponse;
import com.unionmate.backend.domain.council.application.usecase.CouncilManageUsecase;
import com.unionmate.backend.domain.council.application.usecase.CouncilMemberManageUsecase;
import com.unionmate.backend.global.response.CommonResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Council", description = "학생회 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/backend/councils")
public class CouncilController {
	private final CouncilManageUsecase councilManageUsecase;
	private final CouncilMemberManageUsecase councilMemberManageUsecase;

	@Operation(summary = "학생회 생성", description = "새로운 학생회를 생성합니다.")
	@PostMapping()
	public CommonResponse<CreateCouncilResponse> createCouncil(@RequestHeader long memberId, @RequestBody CreateCouncilRequest request) {
		CreateCouncilResponse response = councilManageUsecase.createCouncil(memberId, request);
		return CommonResponse.success(CREATE_COUNCIL, response);
	}

	@Operation(summary = "학생회 가입", description = "초대 코드를 통해 학생회에 가입합니다.")
	@PostMapping("/invitation/{invitationCode}")
	public CommonResponse<CreateCouncilResponse> signUpCouncilManager(@RequestHeader long memberId, @PathVariable String invitationCode) {
		CreateCouncilResponse response = councilManageUsecase.signUpCouncilManager(memberId, invitationCode);
		return CommonResponse.success(COUNCIL_MANAGER_SIGNUP, response);
	}

	@Operation(summary = "회장 위임", description = "회장이 권한을 위임합니다.")
	@PatchMapping("/vice")
	public CommonResponse<Void> delegateVice(@RequestHeader long memberId, @RequestBody DelegateViceRequest request) {
		councilMemberManageUsecase.delegateVice(memberId, request.newPresidentId());
		return CommonResponse.success(DELEGATE_VICE);
	}

	@Operation(summary = "학생회 멤버 조회", description = "특정 학생회의 모든 멤버를 조회합니다.")
	@GetMapping("/{councilId}/members")
	public CommonResponse<List<CouncilMemberResponse>> getAllCouncilMembers(@PathVariable long councilId) {
		List<CouncilMemberResponse> response = councilMemberManageUsecase.getAllCouncilMembers(councilId);
		return CommonResponse.success(GET_ALL_COUNCIL_MEMBERS, response);
	}

	@Operation(summary = "학생회 이름 수정", description = "학생회 이름을 수정합니다.")
	@PatchMapping("/{councilId}/names")
	public CommonResponse<UpdateCouncilNameResponse> updateCouncilName(@RequestHeader long memberId, @PathVariable long councilId, @RequestBody UpdateCouncilNameRequest request) {
		UpdateCouncilNameResponse response = councilManageUsecase.updateCouncilName(memberId, councilId, request);
		return CommonResponse.success(UPDATE_COUNCIL_NAME, response);
	}

	@Operation(summary = "초대 코드 수정", description = "학생회 초대 코드를 수정합니다.")
	@PatchMapping("/{councilId}/invitation-codes")
	public CommonResponse<UpdateInvitationCodeResponse> updateInvitationCode(@RequestHeader long memberId, @PathVariable long councilId, @RequestBody UpdateInvitationCodeRequest request) {
		UpdateInvitationCodeResponse response = councilManageUsecase.updateInvitationCode(memberId, councilId, request);
		return CommonResponse.success(UPDATE_INVITATION_CODE, response);
	}

	@Operation(summary = "학생회 멤버 삭제", description = "학생회에서 특정 멤버를 삭제합니다.")
	@DeleteMapping("/members/{councilManagerId}")
	public CommonResponse<Void> removeCouncilMember(@RequestHeader long memberId, @PathVariable long councilManagerId) {
		councilMemberManageUsecase.removeCouncilMember(memberId, councilManagerId);
		return CommonResponse.success(REMOVE_COUNCIL_MEMBER);
	}
}
