package com.unionmate.backend.domain.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unionmate.backend.domain.member.domain.entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
	Optional<School> findByDomain(String domain);
}
