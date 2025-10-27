package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.LongArrayAnswerConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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

	//Single table 전략인데 하위 타입 전용 컬럼인 multiple를 nullable = false로 해서는 안됩니다.
	//items 테이블에 모든 하위 컬럼이 있고, SELECT 타입을 insert할 때 값이 없는데도 not null을 요구한다면 오류가 생깁니다.
	@Column(name = "multiple")
	@Builder.Default
	private boolean multiple = false;

	@Convert(converter = LongArrayAnswerConverter.class)
	@Lob
	// 선택된 SelectItemOption의 PK 리스트
	private Answer<List<Long>> answer;

	public void updateAnswer(Answer<List<Long>> answer) {
		this.answer = answer;
	}

	public static SelectItem createApplicationSelect(Application application, Boolean required, String title,
		Integer order, String description, boolean multiple) {
		return SelectItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.multiple(multiple)
			.build();
	}

	public static SelectItem createRecruitmentSelect(Recruitment recruitment, Boolean required, String title,
		Integer order, String description, boolean multiple) {
		return SelectItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.multiple(multiple)
			.build();
	}
}
