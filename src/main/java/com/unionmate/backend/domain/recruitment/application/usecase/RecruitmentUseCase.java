package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.applicant.application.exception.ItemNotFoundException;
import com.unionmate.backend.domain.applicant.application.exception.ItemTypeMismatchException;
import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateAnnouncementRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateCalendarRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateSelectOptionRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateSelectRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateTextRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.ItemResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.exception.ActiveRecruitmentCannotDeleteException;
import com.unionmate.backend.domain.recruitment.application.exception.NotRecruitmentCouncilMemberException;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentDeleteService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentGetService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentSaveService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentFormUpdateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUseCase {
	private final CouncilManagerGetService councilManagerGetService;
	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentGetService recruitmentGetService;
	private final RecruitmentFormUpdateService recruitmentFormUpdateService;
	private final RecruitmentDeleteService recruitmentDeleteService;

	@Transactional
	public void createRecruitment(Long memberId, CreateRecruitmentRequest createRecruitmentRequest) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		Council council = councilManager.getCouncil();

		Recruitment recruitment = Recruitment.createRecruitment(council, createRecruitmentRequest.name(),
			LocalDateTime.now(), createRecruitmentRequest.endAt(), createRecruitmentRequest.isActive(),
			createRecruitmentRequest.recruitmentStatus());

		if (createRecruitmentRequest.items() != null) {
			for (CreateItemRequest createItemRequest : createRecruitmentRequest.items()) {
				Item item = createItem(recruitment, createItemRequest);
				recruitment.getItems().add(item);
			}
		}
		recruitmentSaveService.save(recruitment);
	}

	@Transactional
	public void updateRecruitment(Long memberId, Long recruitmentId,
		UpdateRecruitmentRequest updateRecruitmentRequest) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		if (!councilManager.getCouncil().getId().equals(recruitment.getCouncil().getId())) {
			throw new NotRecruitmentCouncilMemberException();
		}

		recruitmentFormUpdateService.updateRecruitment(recruitment, updateRecruitmentRequest);

		// 현재 항목
		Map<Long, Item> items = recruitment.getItems().stream()
			.collect(Collectors.toMap(Item::getId, item -> item));

		// 생성
		if (updateRecruitmentRequest.addItems() != null) {
			for (CreateItemRequest createItemRequest : updateRecruitmentRequest.addItems()) {
				Item item = createItem(recruitment, createItemRequest);
				recruitment.getItems().add(item);
				items.put(item.getId(), item);
			}
		}

		// 수정
		updateItem(items, updateRecruitmentRequest);

		// 삭제
		removeItem(recruitment, items, updateRecruitmentRequest);

		recruitmentSaveService.save(recruitment);
	}

	@Transactional
	public void deleteRecruitment(Long memberId, Long recruitmentId) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		if (!councilManager.getCouncil().getId().equals(recruitment.getCouncil().getId())) {
			throw new NotRecruitmentCouncilMemberException();
		}

		if (Boolean.TRUE.equals(recruitment.getIsActive())) {
			throw new ActiveRecruitmentCannotDeleteException();
		}

		recruitmentDeleteService.deleteRecruitment(recruitmentId);
	}

	public RecruitmentResponse getRecruitmentForm(Long memberId, Long recruitmentId) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);

		if (!councilManager.getCouncil().getId().equals(recruitment.getCouncil().getId())) {
			throw new NotRecruitmentCouncilMemberException();
		}

		List<ItemResponse> items = recruitment.getItems().stream()
			.map(ItemResponse::from)
			.toList();
		return RecruitmentResponse.from(recruitment, items);
	}

	private Item createItem(Recruitment recruitment, CreateItemRequest createItemRequest) {
		boolean required = Boolean.TRUE.equals(createItemRequest.required());
		boolean multiple = Boolean.TRUE.equals(createItemRequest.multiple());

		return switch (createItemRequest.type()) {
			case TEXT -> TextItem.createRecruitmentText(recruitment, required, createItemRequest.title(),
				createItemRequest.order(), createItemRequest.description(), createItemRequest.maxLength());

			case SELECT -> {
				SelectItem selectItem = SelectItem.createRecruitmentSelect(recruitment, required,
					createItemRequest.title(), createItemRequest.order(), createItemRequest.description(), multiple);

				if (createItemRequest.options() != null) {
					for (SelectOptionRequest selectOptionRequest : createItemRequest.options()) {
						selectItem.getSelectItemOptions().add(
							SelectItemOption.createRecruitmentSelectOption(selectOptionRequest.title(),
								selectOptionRequest.order(), Boolean.TRUE.equals(selectOptionRequest.isEtc()),
								selectOptionRequest.etcTitle(), selectItem));
					}
				}
				yield selectItem;
			}

			case CALENDAR -> CalendarItem.createRecruitmentCalendar(recruitment, required, createItemRequest.title(),
				createItemRequest.order(), createItemRequest.description(), createItemRequest.date());

			case ANNOUNCEMENT ->
				AnnouncementItem.createRecruitmentAnnouncement(recruitment, required, createItemRequest.title(),
					createItemRequest.order(), createItemRequest.description(), createItemRequest.announcement());
		};
	}

	private void updateItem(Map<Long, Item> items, UpdateRecruitmentRequest updateRecruitmentRequest) {
		if (updateRecruitmentRequest.updateItems() != null) {
			for (UpdateItemRequest updateItemRequest : updateRecruitmentRequest.updateItems()) {
				Item existItem = items.get(updateItemRequest.id());
				if (existItem == null) {
					throw new ItemNotFoundException();
				}

				recruitmentFormUpdateService.updateCommonItem(existItem, updateItemRequest);

				switch (existItem) {
					case TextItem textItem when updateItemRequest instanceof UpdateTextRequest updateTextRequest ->
						textItem.updateMaxLength(updateTextRequest.maxLength());
					case
						SelectItem selectItem when updateItemRequest instanceof UpdateSelectRequest updateSelectRequest -> {
						selectItem.updateMultiple(updateSelectRequest.multiple());

						// 삭제
						if (updateSelectRequest.removeOptions() != null
							&& !updateSelectRequest.removeOptions().isEmpty()) {
							Set<Long> toRemove = new HashSet<>(updateSelectRequest.removeOptions());

							//현재 존재하는 옵션의 id
							Set<Long> existOptions = selectItem.getSelectItemOptions().stream()
								.map(SelectItemOption::getId)
								.collect(Collectors.toSet());

							if (!existOptions.containsAll(toRemove)) {
								throw new ItemNotFoundException();
							}

							selectItem.getSelectItemOptions()
								.removeIf(selectItemOption -> toRemove.contains(selectItemOption.getId()));
						}

						// 생성, 수정
						if (updateSelectRequest.updateOptions() != null && !updateSelectRequest.updateOptions()
							.isEmpty()) {
							Map<Long, SelectItemOption> selectOptions = selectItem.getSelectItemOptions().stream()
								.filter(options -> options.getId() != null)
								.collect(Collectors.toMap(SelectItemOption::getId, options -> options));

							for (UpdateSelectOptionRequest updateSelectOptionRequest : updateSelectRequest.updateOptions()) {
								// 생성
								if (updateSelectOptionRequest.id() == null) {
									SelectItemOption newOptions = SelectItemOption.createRecruitmentSelectOption(
										updateSelectOptionRequest.title(), updateSelectOptionRequest.order(),
										Boolean.TRUE.equals(updateSelectOptionRequest.isEtc()),
										updateSelectOptionRequest.etcTitle(), selectItem
									);

									selectItem.getSelectItemOptions().add(newOptions);
								} else { // 수정
									SelectItemOption updateOptions = selectOptions.get(updateSelectOptionRequest.id());
									if (updateOptions == null) {
										throw new ItemNotFoundException();
									}
									recruitmentFormUpdateService.updateSelectOptions(updateOptions,
										updateSelectOptionRequest);
								}
							}
						}
					}
					case
						CalendarItem calendarItem when updateItemRequest instanceof UpdateCalendarRequest updateCalendarRequest ->
						calendarItem.updateDate(updateCalendarRequest.date());
					case
						AnnouncementItem announcementItem when updateItemRequest instanceof UpdateAnnouncementRequest updateAnnouncementRequest ->
						announcementItem.updateAnnouncement(updateAnnouncementRequest.announcement());
					default -> throw new ItemTypeMismatchException();
				}
			}
		}
	}

	private void removeItem(Recruitment recruitment, Map<Long, Item> items,
		UpdateRecruitmentRequest updateRecruitmentRequest) {
		if (updateRecruitmentRequest.removeItems() != null) {
			for (Long removeItemId : updateRecruitmentRequest.removeItems()) {
				Item item = items.get(removeItemId);
				if (item == null) {
					throw new ItemNotFoundException();
				}

				recruitment.getItems().remove(item);
				items.remove(removeItemId);
			}
		}
	}
}
