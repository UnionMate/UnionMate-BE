package com.unionmate.backend.domain.applicant.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.applicant.domain.entity.Application;

public interface ApplicantRepository extends JpaRepository<Application, Long> {
}
