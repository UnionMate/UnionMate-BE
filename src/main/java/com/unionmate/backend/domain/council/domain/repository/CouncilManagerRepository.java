package com.unionmate.backend.domain.council.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.member.domain.entity.Member;

@Repository
public interface CouncilManagerRepository extends JpaRepository<CouncilManager, Long> {
	boolean existsByMember(Member member);
	Optional<CouncilManager> findByMember(Member member);
}
