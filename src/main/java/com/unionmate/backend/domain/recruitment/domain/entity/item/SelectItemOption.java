package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "select_item_options")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SelectItemOption extends BaseEntity {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "is_etc", nullable = false)
	@Builder.Default
	private Boolean isEtc = false;

	@Column(name = "etc_title")
	private String etcTitle;

	@Column(name = "orders", nullable = false)
	private Integer order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "select_item_id")
	@Setter
	private SelectItem selectItem;

	@AssertTrue
	public boolean validateEtc() {
		if (Boolean.TRUE.equals(isEtc)) {
			return this.etcTitle != null && !this.etcTitle.isEmpty();
		}
		return true;
	}

	public static SelectItemOption createRecruitmentSelectOption(String title, Integer order, Boolean isEtc,
		String etcTitle, SelectItem selectItem) {
		return SelectItemOption.builder()
			.title(title)
			.order(order)
			.isEtc(isEtc)
			.etcTitle(etcTitle)
			.selectItem(selectItem)
			.build();
	}
}
