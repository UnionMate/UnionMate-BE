package com.unionmate.backend.domain.applicant.domain.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.exception.CommentForbiddenException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.Comment;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {

	private final CommentGetService commentGetService;

	@Transactional
	public void update(Application application, Long commentId, CouncilManager requestCouncilManager,
		String newContent) {
		Long managerCouncilId = requestCouncilManager.getCouncil().getId();
		Long applicationCouncilId = application.getRecruitment().getCouncil().getId();

		if (!Objects.equals(managerCouncilId, applicationCouncilId)) {
			throw new CommentForbiddenException();
		}

		Comment comment = commentGetService.getByIdAndApplication(commentId, application);
		Long commentCouncilId = comment.getCouncilManager().getCouncil().getId();

		if (!Objects.equals(commentCouncilId, managerCouncilId)) {
			throw new CommentForbiddenException();
		}

		comment.updateContent(newContent);
	}
}
