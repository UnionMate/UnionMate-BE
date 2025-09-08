package com.unionmate.backend.domain.recruitment.entity.item;

import com.unionmate.backend.domain.applicant.entity.Application;
import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.recruitment.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.entity.Recruitment;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Item extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "item_type", insertable = false, updatable = false)
  private ItemType itemType;

  @Column(name = "required", nullable = false)
  private Boolean required;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "orders", nullable = false)
  private Integer order;

  @Column(name = "description")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  private Recruitment recruitment;

  @ManyToOne(fetch = FetchType.LAZY)
  private Application application;
}
