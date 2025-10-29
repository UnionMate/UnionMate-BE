package com.unionmate.backend.domain.applicant.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.application.exception.CommentNotFoundException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.Comment;
import com.unionmate.backend.domain.applicant.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentGetService {
	private final CommentRepository commentRepository;

	public Comment getById(Long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(CommentNotFoundException::new);
	}

	public Comment getByIdAndApplication(Long commentId, Application application) {
		return commentRepository.findByIdAndApplication(commentId, application)
			.orElseThrow(CommentNotFoundException::new);
	}

	public List<Comment> getAllByApplication(Application application) {
		return commentRepository.findAllByApplicationOrderByCreatedAtDesc(application);
	}
}
