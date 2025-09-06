package com.unionmate.backend.domain.recruitment.entity.item;

import com.unionmate.backend.domain.recruitment.entity.enums.ItemType.DiscriminationValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DiscriminatorValue(DiscriminationValue.SELECT)
public class SelectItem extends Item {

  @OneToMany(mappedBy = "selectItem", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<SelectItemOption> selectItemOptions = new ArrayList<>();
}
