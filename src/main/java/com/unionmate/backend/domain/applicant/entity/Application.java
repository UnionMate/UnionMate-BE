package com.unionmate.backend.domain.applicant.entity;

import com.unionmate.backend.domain.applicant.entity.embed.Interview;
import com.unionmate.backend.domain.recruitment.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.entity.item.Item;
import com.unionmate.backend.global.entity.BaseEntity;
import com.unionmate.backend.domain.applicant.entity.embed.Stage;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "applications")
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Application extends BaseEntity {

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "tel", nullable = false)
  private String tel;

  @Embedded
  @Builder.Default
  private Interview interview = Interview.init();

  @Embedded
  @Builder.Default
  private Stage stage = Stage.init();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "recruitment_id", nullable = false)
  private Recruitment recruitment;

  @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<Item> answers = new ArrayList<>();
}
