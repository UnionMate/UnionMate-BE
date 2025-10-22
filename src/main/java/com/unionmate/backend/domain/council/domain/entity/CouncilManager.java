package com.unionmate.backend.domain.council.domain.entity;

import com.unionmate.backend.domain.council.domain.entity.enums.CouncilRole;
import com.unionmate.backend.domain.council.exception.DifferentCouncilException;
import com.unionmate.backend.domain.council.exception.NotCouncilViceException;
import com.unionmate.backend.domain.council.exception.ViceCannotLeaveException;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.entity.School;
import com.unionmate.backend.global.entity.BaseEntity;

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

	public static CouncilManager createMember(Member member, School school, Council council) {
		return CouncilManager.builder()
			.member(member)
			.school(school)
			.council(council)
			.councilRole(CouncilRole.MEMBER)
			.build();
	}

	public static CouncilManager createVice(Member member, School school, Council council) {
		return CouncilManager.builder()
			.member(member)
			.school(school)
			.council(council)
			.councilRole(CouncilRole.VICE)
			.build();
	}

	public void isViceOfCouncil(Council council) {
		if (this.councilRole != CouncilRole.VICE && this.council == council) {
			throw new NotCouncilViceException();
		}
	}

	public void isVice() {
		if (this.councilRole != CouncilRole.VICE) {
			throw new NotCouncilViceException();
		}
	}

	public void validateSameCouncil(CouncilManager other) {
		if (!this.council.getId().equals(other.getCouncil().getId())) {
			throw new DifferentCouncilException();
		}
	}

	public void delegateToMember() {
		this.councilRole = CouncilRole.MEMBER;
	}

	public void promoteToVice() {
		this.councilRole = CouncilRole.VICE;
	}

	public void validateNotVice() {
		if (this.councilRole == CouncilRole.VICE) {
			throw new ViceCannotLeaveException();
		}
	}
}
