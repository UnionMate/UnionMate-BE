package com.unionmate.backend.domain.applicant.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.dto.request.AnswerRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.CalendarAnswerRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.CreateApplicantRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.SelectAnswerRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.TextAnswerRequest;
import com.unionmate.backend.domain.applicant.application.exception.ItemNotFoundException;
import com.unionmate.backend.domain.applicant.application.exception.ItemTypeMismatchException;
import com.unionmate.backend.domain.applicant.application.exception.OptionInvalidException;
import com.unionmate.backend.domain.applicant.application.exception.RecruitmentInvalidException;
import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
import com.unionmate.backend.domain.applicant.application.exception.TextTooLongException;
import com.unionmate.backend.domain.applicant.application.mapper.ApplicationRequestMapper;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationSaveService;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.enums.ItemType;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentGetService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationUseCase {
	private final ApplicationSaveService applicationSaveService;
	private final RecruitmentGetService recruitmentGetService;
	private final ApplicationRequestMapper applicationRequestMapper;

	@Transactional
	public void submitApplication(Long recruitmentId, CreateApplicantRequest createApplicantRequest) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		validateRecruitmentOpen(recruitment);

		Map<Long, Item> templateById = recruitment.getItems()
			.stream().collect(Collectors.toMap(Item::getId, item -> item));

		Application application = applicationRequestMapper.toApplication(
			createApplicantRequest.name(), createApplicantRequest.email(), createApplicantRequest.tel(), recruitment
		);

		Set<Long> answerIds = new HashSet<>();
		if (createApplicantRequest.answers() != null) {
			for (AnswerRequest answer : createApplicantRequest.answers()) {
				Item itemTemplate = templateById.get(answer.itemId());
				if (itemTemplate == null) {
					throw new ItemNotFoundException();
				}

				switch (itemTemplate.getItemType()) {
					case TEXT -> {
						if (!(answer instanceof TextAnswerRequest textAnswerRequest)
							|| !(itemTemplate instanceof TextItem textItem)) {
							throw new ItemTypeMismatchException();
						}

						String text = textAnswerRequest.text();
						if (Boolean.TRUE.equals(textItem.getRequired()) && (text == null || text.isBlank())) {
							throw new RequiredAnswerMissingException();
						}
						if (textItem.getMaxLength() != null && text != null
							&& text.length() > textItem.getMaxLength()) {
							throw new TextTooLongException();
						}

						TextItem textAnswer = applicationRequestMapper.toTextItem(
							application, textItem.getRequired(), textItem.getTitle(), textItem.getOrder(),
							textItem.getDescription(), textItem.getMaxLength(), textItem.getItemType()
						);

						writeTextAnswer(textAnswer, text);
						application.getAnswers().add(textAnswer);
						answerIds.add(textAnswer.getId());
					}

					case SELECT -> {
						if (!(answer instanceof SelectAnswerRequest selectAnswerRequest)
							|| !(itemTemplate instanceof SelectItem selectItem)) {
							throw new ItemTypeMismatchException();
						}

						List<Long> selection = Optional.ofNullable(selectAnswerRequest.optionIds()).orElse(List.of());

						if (Boolean.TRUE.equals(selectItem.getRequired()) && selection.isEmpty()) {
							throw new RequiredAnswerMissingException();
						}
						if (!selectItem.isMultiple() && selection.size() > 1) {
							throw new OptionInvalidException();
						}

						SelectItem selectAnswer = applicationRequestMapper.toSelectItem(
							application, selectItem.getRequired(), selectItem.getTitle(), selectItem.getOrder(),
							selectItem.getDescription(), selectItem.isMultiple(), selectItem.getItemType()
						);

						writeSelectAnswer(selectAnswer, selection);
						application.getAnswers().add(selectAnswer);
						answerIds.add(selectAnswer.getId());
					}

					case CALENDAR -> {
						if (!(answer instanceof CalendarAnswerRequest calendarAnswerRequest)
							|| !(itemTemplate instanceof CalendarItem calendarItem)) {
							throw new ItemTypeMismatchException();
						}

						LocalDate date = calendarAnswerRequest.date();

						if (Boolean.TRUE.equals(calendarItem.getRequired()) && date == null) {
							throw new RequiredAnswerMissingException();
						}

						CalendarItem calendarAnswer = applicationRequestMapper.toCalendarItem(
							application, calendarItem.getRequired(), calendarItem.getTitle(), calendarItem.getOrder(),
							calendarItem.getDescription(), calendarItem.getDate(), calendarItem.getItemType()
						);

						writeCalendarAnswer(calendarAnswer, date);
						application.getAnswers().add(calendarAnswer);
						answerIds.add(calendarAnswer.getId());
					}
				}
			}
		}

		boolean isItemAllWritten = recruitment.getItems().stream()
			.filter(item -> item.getItemType() != ItemType.ANNOUNCEMENT)
			.filter(item -> Boolean.TRUE.equals(item.getRequired()))
			.anyMatch(item -> !answerIds.contains(item.getId()));
		if (isItemAllWritten) {
			throw new RequiredAnswerMissingException();
		}

		applicationSaveService.save(application);
	}

	private void validateRecruitmentOpen(Recruitment recruitment) {
		LocalDateTime now = LocalDateTime.now();
		if (Boolean.FALSE.equals(recruitment.getIsActive()) || now.isBefore(recruitment.getStartAt())
			|| now.isAfter(recruitment.getEndAt())) {
			throw new RecruitmentInvalidException();
		}
	}

	private void writeTextAnswer(TextItem textItem, String value) {
		textItem.updateAnswer(new Answer<>(value));
	}

	private void writeSelectAnswer(SelectItem selectItem, List<Long> value) {
		selectItem.updateAnswer(new Answer<>(value));
	}

	private void writeCalendarAnswer(CalendarItem calendarItem, LocalDate value) {
		calendarItem.updateAnswer(new Answer<>(value));
	}
}
