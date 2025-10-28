package com.unionmate.backend.domain.applicant.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unionmate.backend.domain.applicant.domain.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	List<Application> findAllByNameAndEmailOrderByIdDesc(String name, String email);
}
