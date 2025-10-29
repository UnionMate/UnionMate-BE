package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.ItemResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.exception.NotRecruitmentCouncilMemberException;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentGetService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentSaveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUseCase {
	private final CouncilManagerGetService councilManagerGetService;
	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentGetService recruitmentGetService;

	@Transactional
	public void createRecruitment(Long memberId, CreateRecruitmentRequest createRecruitmentRequest) {
		CouncilManager councilManager;
		councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

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

	public RecruitmentResponse getRecruitmentForm(Long memberId, Long id) {
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(id);

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
}
