package com.unionmate.backend.domain.member.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.member.domain.entity.School;
import com.unionmate.backend.domain.member.domain.repository.SchoolRepository;
import com.unionmate.backend.domain.member.exception.InvalidEmailFormatException;
import com.unionmate.backend.domain.member.exception.SchoolNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolGetService {
	private final SchoolRepository schoolRepository;

	public School getSchoolByEmailDomain(String email) {
		String domain = extractDomainFromEmail(email);
		return schoolRepository.findByDomain(domain)
			.orElseThrow(SchoolNotFoundException::new);
	}

	//TODO: 이메일 형식 검증 로직 강화 필요
	private String extractDomainFromEmail(String email) {
		String[] parts = email.split("@");
		if (parts.length != 2) {
			throw new InvalidEmailFormatException();
		}
		String fullDomain = parts[1];
		String[] domainParts = fullDomain.split("\\.");
		return domainParts[0];
	}
}
