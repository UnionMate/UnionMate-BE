package com.unionmate.backend.domain.council.domain.entity;

import java.util.UUID;

import com.unionmate.backend.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	name = "councils",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_invitation_code",
			columnNames = {"invitation_code"}
		)
	}
)
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Council extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "invitation_code", nullable = false, length = 36)
	private String invitationCode;

	public static Council createCouncil(String name) {
		String invitationCode = UUID.randomUUID().toString();
		return Council.builder()
			.name(name)
			.invitationCode(invitationCode)
			.build();
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
}
