package com.unionmate.backend.domain.recruitment.domain.entity.item;

import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType.DiscriminationValue;
import com.unionmate.backend.global.converter.StringAnswerConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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
@DiscriminatorValue(DiscriminationValue.TEXT)
public class TextItem extends Item {

	@Column(name = "text_max_length")
	private Integer maxLength;

	@Convert(converter = StringAnswerConverter.class)
	@Lob
	private Answer<String> answer;

	public void updateAnswer(Answer<String> answer) {
		this.answer = answer;
	}

	public static TextItem createApplicationText(Application application, Boolean required, String title, Integer order,
		String description, Integer maxLength, ItemType itemType) {
		return TextItem.builder()
			.application(application)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.maxLength(maxLength)
			.itemType(itemType)
			.build();
	}

	public static TextItem createRecruitmentText(Recruitment recruitment, Boolean required, String title, Integer order,
		String description, Integer maxLength) {
		return TextItem.builder()
			.recruitment(recruitment)
			.required(required)
			.title(title)
			.order(order)
			.description(description)
			.maxLength(maxLength)
			.build();
	}
}
