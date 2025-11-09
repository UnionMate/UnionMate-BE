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

	@Column(name = "orders", nullable = false)
	private Integer order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "select_item_id")
	@Setter
	private SelectItem selectItem;

	public void updateTitle(String title) {
		if (title != null) {
			this.title = title;
		}
	}

	public void updateOrder(Integer order) {
		if (order != null) {
			this.order = order;
		}
	}

	public static SelectItemOption createRecruitmentSelectOption(String title, Integer order, SelectItem selectItem) {
		return SelectItemOption.builder()
			.title(title)
			.order(order)
			.selectItem(selectItem)
			.build();
	}
}
