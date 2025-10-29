package com.unionmate.backend.domain.applicant.application.usecase;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.CreateCommentRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateCommentRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.CommentResponse;
import com.unionmate.backend.domain.applicant.application.exception.CommentForbiddenException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.Comment;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
import com.unionmate.backend.domain.applicant.domain.service.CommentCreateService;
import com.unionmate.backend.domain.applicant.domain.service.CommentDeleteService;
import com.unionmate.backend.domain.applicant.domain.service.CommentGetService;
import com.unionmate.backend.domain.applicant.domain.service.CommentUpdateService;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentUseCase {

	private final CommentCreateService commentCreateService;

	private final ApplicationGetService applicationGetService;
	private final CouncilManagerGetService councilManagerGetService;
	private final CommentGetService commentGetService;

	private final CommentUpdateService commentUpdateService;

	private final CommentDeleteService commentDeleteService;

	@Transactional
	public void createComment(Long memberId, Long applicationId, CreateCommentRequest createCommentRequest) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateSameCouncil(councilManager, application);
		Comment comment = Comment.createContent(application, councilManager, createCommentRequest.content());

		commentCreateService.create(comment);
	}

	@Transactional
	public void updateComment(Long memberId, Long applicationId, Long commentId,
		UpdateCommentRequest updateCommentRequest) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		commentUpdateService.update(application, commentId, councilManager, updateCommentRequest.content());
	}

	public List<CommentResponse> getComments(Long memberId, Long applicationId) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateSameCouncil(councilManager, application);

		return CommentResponse.fromList(commentGetService.getAllByApplication(application));
	}

	@Transactional
	public void deleteComment(Long memberId, Long applicationId, Long commentId) {
		Application application = applicationGetService.getApplicationById(applicationId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		councilManager.isVice();
		validateSameCouncil(councilManager, application);

		Comment comment = commentGetService.getByIdAndApplication(commentId, application);

		if (!Objects.equals(
			comment.getCouncilManager().getCouncil().getId(),
			councilManager.getCouncil().getId())) {
			throw new CommentForbiddenException();
		}

		commentDeleteService.delete(comment);
	}

	private void validateSameCouncil(CouncilManager councilManager, Application application) {
		Long managerCouncilId = councilManager.getCouncil().getId();
		Long applicationCouncilId = application.getRecruitment().getCouncil().getId();

		if (!Objects.equals(managerCouncilId, applicationCouncilId)) {
			throw new CommentForbiddenException();
		}
	}
}
