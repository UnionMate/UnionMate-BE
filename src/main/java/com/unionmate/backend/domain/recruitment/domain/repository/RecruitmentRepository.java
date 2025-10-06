package com.unionmate.backend.domain.recruitment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
}
