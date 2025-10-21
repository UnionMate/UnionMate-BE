package com.unionmate.backend.domain.council.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionmate.backend.domain.council.domain.entity.Council;

@Repository
public interface CouncilRepository extends JpaRepository<Council, Long> {
	boolean existsByInvitationCode(String invitationCode);

	Optional<Council> findByInvitationCode(String invitationCode);
}
