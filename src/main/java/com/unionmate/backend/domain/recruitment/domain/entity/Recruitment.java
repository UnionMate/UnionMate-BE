package com.unionmate.backend.domain.recruitment.domain.entity;

import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.RecruitmentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "recruitments")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Recruitment extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "start_at", nullable = false)
	@Builder.Default
	private LocalDateTime startAt = LocalDateTime.now();

	@Column(name = "end_at", nullable = false)
	private LocalDateTime endAt;

	@Column(name = "is_active", nullable = false)
	@Builder.Default
	private Boolean isActive = false;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RecruitmentStatus recruitmentStatus;

	@OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<Item> items = new ArrayList<>();

	public boolean isOpen(LocalDateTime now) {
		return Boolean.TRUE.equals(isActive)
			&& !now.isBefore(startAt)
			&& !now.isAfter(endAt);
	}

	public static Recruitment createRecruitment(String name, LocalDateTime startAt, LocalDateTime endAt,
		Boolean isActive, RecruitmentStatus recruitmentStatus) {
		return Recruitment.builder()
			.name(name)
			.startAt(startAt)
			.endAt(endAt)
			.isActive(isActive)
			.recruitmentStatus(recruitmentStatus)
			.build();
	}
}
