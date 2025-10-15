package com.unionmate.backend.domain.applicant.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationSaveService {
	private final ApplicationRepository applicationRepository;

	public void save(Application application) {
		applicationRepository.save(application);
	}
}
