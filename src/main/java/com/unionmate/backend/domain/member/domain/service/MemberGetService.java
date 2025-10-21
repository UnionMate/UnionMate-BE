package com.unionmate.backend.domain.member.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.repository.MemberRepository;
import com.unionmate.backend.domain.member.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGetService {
	private final MemberRepository memberRepository;

	public Member getMemberById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(MemberNotFoundException::new);
	}
}
