package com.unionmate.backend.domain.member.domain.service;

import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberSaveService {

  private final MemberRepository memberRepository;

  public Member save(Member member) {
    return this.memberRepository.save(member);
  }
}
