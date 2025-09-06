package com.unionmate.backend.domain.recruitment.entity.item;

import com.unionmate.backend.domain.recruitment.entity.enums.ItemType.DiscriminationValue;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue(DiscriminationValue.ANNOUNCEMENT)
public class AnnouncementItem extends Item {

  @Column(name = "announcement", nullable = false, length = 500)
  private String announcement;
}
