package com.unionmate.backend.domain.member.entity;

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
    name = "schools",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_school_name",
            columnNames = {"name"}
        ),
        @UniqueConstraint(
            name = "uk_school_domain",
            columnNames = {"domain"}
        )
    }
)
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class School extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "domain", nullable = false)
  private String domain;
}
