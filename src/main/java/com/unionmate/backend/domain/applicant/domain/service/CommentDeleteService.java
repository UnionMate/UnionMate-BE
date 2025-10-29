package com.unionmate.backend.domain.applicant.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.domain.entity.Comment;
import com.unionmate.backend.domain.applicant.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
	private final CommentRepository commentRepository;

	@Transactional
	public void delete(Comment comment) {
		commentRepository.delete(comment);
	}
}