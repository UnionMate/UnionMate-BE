package com.unionmate.backend.domain.applicant.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.application.exception.ApplicationNotFoundException;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationGetService {
	private final ApplicationRepository applicationRepository;

	public List<Application> getMyApplications(String name, String email) {
		return applicationRepository.findAllByNameAndEmailOrderByIdDesc(name, email);
	}

	public Application getApplicationById(Long applicationId) {
		return applicationRepository.findById(applicationId)
			.orElseThrow(ApplicationNotFoundException::new);
	}
}
