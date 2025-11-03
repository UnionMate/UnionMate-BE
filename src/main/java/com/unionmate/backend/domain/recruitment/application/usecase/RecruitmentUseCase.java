package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.council.domain.entity.Council;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.council.exception.DifferentCouncilException;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.ToggleRecruitmentActivationRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.ItemResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.dto.response.ToggleRecruitmentActivationResponse;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentGetService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentSaveService;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentUpdateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUseCase {

	private final CouncilManagerGetService councilManagerGetService;
	private final RecruitmentGetService recruitmentGetService;

	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentUpdateService recruitmentUpdateService;

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
	public ToggleRecruitmentActivationResponse toggleRecruitmentActivation(Long memberId, Long recruitmentId,
		ToggleRecruitmentActivationRequest toggleRecruitmentActivationRequest,
		LocalDateTime now
	) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(recruitmentId);
		CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(memberId);

		validateSameCouncil(councilManager, recruitment);

		recruitmentUpdateService.activateIfAllowed(recruitment, toggleRecruitmentActivationRequest.active(), now);
		boolean open = recruitment.isOpen(now);

		return ToggleRecruitmentActivationResponse.of(
			recruitment.getId(),
			Boolean.TRUE.equals(recruitment.getIsActive()),
			open,
			recruitment.getStartAt(),
			recruitment.getEndAt()
		);
	}

	public RecruitmentResponse getRecruitmentForm(Long id) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(id);

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

	private void validateSameCouncil(CouncilManager councilManager, Recruitment recruitment) {
		Long managerCouncilId = councilManager.getCouncil().getId();
		Long recruitmentCouncilId = recruitment.getCouncil().getId();
		if (!managerCouncilId.equals(recruitmentCouncilId)) {

			throw new DifferentCouncilException();
		}
	}
}
