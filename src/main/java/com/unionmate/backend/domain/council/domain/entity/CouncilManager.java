package com.unionmate.backend.domain.council.domain.entity;

import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.entity.School;
import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.council.domain.entity.enums.CouncilRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
    name = "council_managers",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_council_member",
            columnNames = {"member_id", "council_id"}
        )
    }
)
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CouncilManager extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id", nullable = false)
  private School school;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "council_id", nullable = false)
  private Council council;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private CouncilRole councilRole = CouncilRole.MEMBER;

  public static CouncilManager LinkToMember(Member member, School school, Council council) {
    return CouncilManager.builder()
        .member(member)
        .school(school)
        .council(council)
        .councilRole(CouncilRole.MEMBER)
        .build();
  }

  public static CouncilManager LinkToVice(Member member, School school, Council council) {
    return CouncilManager.builder()
        .member(member)
        .school(school)
        .council(council)
        .councilRole(CouncilRole.VICE)
        .build();
  }
}
