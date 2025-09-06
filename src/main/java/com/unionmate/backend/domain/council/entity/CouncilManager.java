package com.unionmate.backend.domain.council.entity;

import com.unionmate.backend.domain.member.entity.Member;
import com.unionmate.backend.domain.member.entity.School;
import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.council.entity.enums.CouncilRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "council_managers")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouncilManager extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "members_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "schools_id")
  private School school;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "council_id")
  private Council council;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CouncilRole councilRole;
}
