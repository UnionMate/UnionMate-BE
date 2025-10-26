package com.unionmate.backend.domain.applicant.application.usecase;

import com.unionmate.backend.domain.applicant.application.dto.request.GetMyApplicationsRequest;
import com.unionmate.backend.domain.applicant.application.dto.request.UpdateApplicationRequest;
import com.unionmate.backend.domain.applicant.application.dto.response.GetApplicationResponse;
import com.unionmate.backend.domain.applicant.application.dto.response.GetMyApplicationsResponse;
import com.unionmate.backend.domain.applicant.application.exception.ApplicationUpdateInvalidException;
import com.unionmate.backend.domain.applicant.application.exception.DuplicateItemAnswerException;
import com.unionmate.backend.domain.applicant.application.util.CalendarAnswerValidator;
import com.unionmate.backend.domain.applicant.application.util.SelectAnswerValidator;
import com.unionmate.backend.domain.applicant.application.util.TextAnswerValidator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.unionmate.backend.domain.applicant.application.util.UpdateAnswerValidator;
import com.unionmate.backend.domain.applicant.domain.entity.Application;
import com.unionmate.backend.domain.applicant.domain.entity.column.Answer;
import com.unionmate.backend.domain.applicant.domain.service.ApplicationGetService;
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
@Transactional(readOnly = true)
public class ApplicationUseCase {
	private final ApplicationSaveService applicationSaveService;
	private final RecruitmentGetService recruitmentGetService;
	private final ApplicationGetService applicationGetService;
	private final TextAnswerValidator textAnswerValidator;
	private final SelectAnswerValidator selectAnswerValidator;
	private final CalendarAnswerValidator calendarAnswerValidator;
	private final UpdateAnswerValidator updateAnswerValidator;

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
							textItem.getDescription(), textItem.getMaxLength()
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
							selectItem.getDescription(), selectItem.isMultiple()
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
							calendarItem.getDescription(), calendarItem.getDate()
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

	@Transactional
	public void updateApplication(Long applicationId, UpdateApplicationRequest updateApplicationRequest) {
		Application application = applicationGetService.getApplicationById(applicationId);
		Recruitment recruitment = application.getRecruitment();

		if (!recruitment.isOpen(LocalDateTime.now())) {
			throw new ApplicationUpdateInvalidException();
		}

		application.updateIfPresent(
			updateApplicationRequest.name(), updateApplicationRequest.email(), updateApplicationRequest.tel()
		);

		Map<Long, Item> templateById = recruitment.getItems()
			.stream().collect(Collectors.toMap(Item::getId, item -> item));

		Map<Long, Item> existingAnswer = ExistingAnswers(application, recruitment);

		if (updateApplicationRequest.answers() != null) {
			Set<Long> seen = new HashSet<>();
			for (AnswerRequest answer : updateApplicationRequest.answers()) {
				Item itemTemplate = templateById.get(answer.itemId());
				if (itemTemplate == null) {
					throw new ItemNotFoundException();
				}

				if (!seen.add(itemTemplate.getId())) {
					throw new DuplicateItemAnswerException();
				}

				switch (itemTemplate.getItemType()) {
					case TEXT -> {
						if (!(answer instanceof TextAnswerRequest textAnswerRequest)
							|| !(itemTemplate instanceof TextItem textItem)) {
							throw new ItemTypeMismatchException();
						}

						textAnswerValidator.validate(textItem, textAnswerRequest);

						Item existingItem = existingAnswer.get(itemTemplate.getId());
						if (existingItem instanceof TextItem item) {
							item.updateAnswer(new Answer<>(textAnswerRequest.text()));
						} else {
							TextItem newTextItem = TextItem.createApplicationText(
								application, textItem.getRequired(), textItem.getTitle(), textItem.getOrder(),
								textItem.getDescription(), textItem.getMaxLength()
							);

							newTextItem.updateAnswer(new Answer<>(textAnswerRequest.text()));
							application.getAnswers().add(newTextItem);
							existingAnswer.put(itemTemplate.getId(), newTextItem);
						}
					}

					case SELECT -> {
						if (!(answer instanceof SelectAnswerRequest selectAnswerRequest)
							|| !(itemTemplate instanceof SelectItem selectItem)) {
							throw new ItemTypeMismatchException();
						}

						selectAnswerValidator.validate(selectItem, selectAnswerRequest);

						List<Long> selection = Optional.ofNullable(selectAnswerRequest.optionIds()).orElse(List.of());

						Item existingItem = existingAnswer.get(itemTemplate.getId());
						if (existingItem instanceof SelectItem item) {
							item.updateAnswer(new Answer<>(selection));
						} else {
							SelectItem newSelectItem = SelectItem.createApplicationSelect(
								application, selectItem.getRequired(), selectItem.getTitle(), selectItem.getOrder(),
								selectItem.getDescription(), selectItem.isMultiple()
							);

							newSelectItem.updateAnswer(new Answer<>(selection));
							application.getAnswers().add(newSelectItem);
							existingAnswer.put(itemTemplate.getId(), newSelectItem);
						}
					}

					case CALENDAR -> {
						if (!(answer instanceof CalendarAnswerRequest calendarAnswerRequest)
							|| !(itemTemplate instanceof CalendarItem calendarItem)) {
							throw new ItemTypeMismatchException();
						}

						calendarAnswerValidator.validate(calendarItem, calendarAnswerRequest);

						Item existingItem = existingAnswer.get(calendarItem.getId());
						if (existingItem instanceof CalendarItem item) {
							item.updateAnswer(new Answer<>(calendarAnswerRequest.date()));
						} else {
							CalendarItem newCalendarItem = CalendarItem.createApplicationCalendar(
								application, calendarItem.getRequired(), calendarItem.getTitle(),
								calendarItem.getOrder(), calendarItem.getDescription(), calendarItem.getDate()
							);

							newCalendarItem.updateAnswer(new Answer<>(calendarAnswerRequest.date()));
							application.getAnswers().add(newCalendarItem);
							existingAnswer.put(itemTemplate.getId(), newCalendarItem);
						}
					}
				}
			}
		}

		updateAnswerValidator.validateRequiredAnswers(application, recruitment);

		applicationSaveService.save(application);
	}

	public List<GetMyApplicationsResponse> getMyApplications(GetMyApplicationsRequest getMyApplicationsRequest) {
		return applicationGetService.getMyApplications(
				getMyApplicationsRequest.name(), getMyApplicationsRequest.email())
			.stream()
			.map(GetMyApplicationsResponse::from)
			.toList();
	}

	public GetApplicationResponse getApplication(Long applicationId) {
		Application application = applicationGetService.getApplicationById(applicationId);

		return GetApplicationResponse.from(application);
	}

	private Map<Long, Item> ExistingAnswers(Application application, Recruitment recruitment) {
		Map<Long, Item> map = new HashMap<>();
		List<Item> answers = application.getAnswers();

		for (Item item : recruitment.getItems()) {
			answers.stream()
				.filter(answer -> answer.getItemType() == item.getItemType()
					&& Objects.equals(answer.getTitle(), item.getTitle())
					&& Objects.equals(answer.getOrder(), item.getOrder()))
				.findFirst().ifPresent(matched -> map.put(item.getId(), matched));
		}
		return map;
	}
}
