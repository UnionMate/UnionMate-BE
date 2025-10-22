package com.unionmate.backend.domain.applicant.application.usecase;

import com.unionmate.backend.domain.applicant.application.exception.DuplicateItemAnswerException;
import com.unionmate.backend.domain.applicant.application.util.CalendarAnswerValidator;
import com.unionmate.backend.domain.applicant.application.util.SelectAnswerValidator;
import com.unionmate.backend.domain.applicant.application.util.TextAnswerValidator;

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
import com.unionmate.backend.domain.applicant.application.exception.RecruitmentInvalidException;
import com.unionmate.backend.domain.applicant.application.exception.RequiredAnswerMissingException;
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
	private final TextAnswerValidator textAnswerValidator;
	private final SelectAnswerValidator selectAnswerValidator;
	private final CalendarAnswerValidator calendarAnswerValidator;

	@Transactional
	public void submitApplication(Long recruitmentId, CreateApplicantRequest createApplicantRequest) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		if (!recruitment.isOpen(LocalDateTime.now())) {
			throw new RecruitmentInvalidException();
		}

		Map<Long, Item> templateById = recruitment.getItems()
			.stream().collect(Collectors.toMap(Item::getId, item -> item));

		Application application = Application.createApplication(
			createApplicantRequest.name(), createApplicantRequest.email(), createApplicantRequest.tel(), recruitment
		);

		Set<Long> answerIds = new HashSet<>();
		if (createApplicantRequest.answers() != null) {
			for (AnswerRequest answer : createApplicantRequest.answers()) {
				Item itemTemplate = templateById.get(answer.itemId());
				if (itemTemplate == null) {
					throw new ItemNotFoundException();
				}

				if (!answerIds.add(answer.itemId())) {
					throw new DuplicateItemAnswerException();
				}

				switch (itemTemplate.getItemType()) {
					case TEXT -> {
						if (!(answer instanceof TextAnswerRequest textAnswerRequest)
							|| !(itemTemplate instanceof TextItem textItem)) {
							throw new ItemTypeMismatchException();
						}

						textAnswerValidator.validate(textItem, textAnswerRequest);

						TextItem textAnswer = TextItem.createApplicationText(
							application, textItem.getRequired(), textItem.getTitle(), textItem.getOrder(),
							textItem.getDescription(), textItem.getMaxLength(), textItem.getItemType()
						);

						textAnswer.updateAnswer(new Answer<>(textAnswerRequest.text()));
						application.getAnswers().add(textAnswer);
						answerIds.add(textItem.getId());
					}

					case SELECT -> {
						if (!(answer instanceof SelectAnswerRequest selectAnswerRequest)
							|| !(itemTemplate instanceof SelectItem selectItem)) {
							throw new ItemTypeMismatchException();
						}

						selectAnswerValidator.validate(selectItem, selectAnswerRequest);

						SelectItem selectAnswer = SelectItem.createApplicationSelect(
							application, selectItem.getRequired(), selectItem.getTitle(), selectItem.getOrder(),
							selectItem.getDescription(), selectItem.isMultiple(), selectItem.getItemType()
						);

						List<Long> selection = Optional.ofNullable(selectAnswerRequest.optionIds()).orElse(List.of());

						selectAnswer.updateAnswer(new Answer<>(selection));
						application.getAnswers().add(selectAnswer);
						answerIds.add(selectItem.getId());
					}

					case CALENDAR -> {
						if (!(answer instanceof CalendarAnswerRequest calendarAnswerRequest)
							|| !(itemTemplate instanceof CalendarItem calendarItem)) {
							throw new ItemTypeMismatchException();
						}

						calendarAnswerValidator.validate(calendarItem, calendarAnswerRequest);

						CalendarItem calendarAnswer = CalendarItem.createApplicationCalendar(
							application, calendarItem.getRequired(), calendarItem.getTitle(), calendarItem.getOrder(),
							calendarItem.getDescription(), calendarItem.getDate(), calendarItem.getItemType()
						);

						calendarAnswer.updateAnswer(new Answer<>(calendarAnswerRequest.date()));
						application.getAnswers().add(calendarAnswer);
						answerIds.add(calendarItem.getId());
					}
				}
			}
		}

		boolean hasMissingRequiredAnswer = recruitment.getItems().stream()
			.filter(item -> item.getItemType() != ItemType.ANNOUNCEMENT)
			.filter(item -> Boolean.TRUE.equals(item.getRequired()))
			.anyMatch(item -> !answerIds.contains(item.getId()));
		if (hasMissingRequiredAnswer) {
			throw new RequiredAnswerMissingException();
		}

		applicationSaveService.save(application);
	}
}
