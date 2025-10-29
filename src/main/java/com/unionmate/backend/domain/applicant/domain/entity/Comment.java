package com.unionmate.backend.domain.applicant.domain.entity;

import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "comments")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id", nullable = false)
  private Application application;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "council_manager_id", nullable = false)
  private CouncilManager councilManager;

  @Column(name = "content", length = 1000, nullable = false)
  private String content;

  public static Comment createContent(Application application, CouncilManager councilManager, String content) {
    return Comment.builder()
        .application(application)
        .councilManager(councilManager)
        .content(content)
        .build();
  }

  public void updateContent(String content) {
    this.content = content;
  }
}
