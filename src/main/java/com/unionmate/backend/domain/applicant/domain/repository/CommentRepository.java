package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@EntityGraph(attributePaths = {"councilManager", "councilManager.member"})
	List<Comment> findAllByApplicationOrderByCreatedAtDesc(Application application);

	@EntityGraph(attributePaths = {"councilManager", "councilManager.member"})
	Optional<Comment> findByIdAndApplication(Long commentId, Application application);
}
