package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.application.dto.response.RecruitmentResponse;
import com.unionmate.backend.domain.recruitment.application.mapper.RecruitmentResponseMapper;
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
	private final RecruitmentSaveService recruitmentSaveService;
	private final RecruitmentGetService recruitmentGetService;
	private final RecruitmentResponseMapper recruitmentResponseMapper;

	@Transactional
	public void createRecruitment(CreateRecruitmentRequest rq) {
		Recruitment recruitment = Recruitment.createRecruitment(rq.name(), LocalDateTime.now(), rq.endAt(),
			rq.isActive(), rq.recruitmentStatus());

		if (rq.items() != null) {
			for (CreateItemRequest ir : rq.items()) {
				Item item = createItem(recruitment, ir);
				recruitment.getItems().add(item);
			}
		}
		recruitmentSaveService.save(recruitment);
	}

	public RecruitmentResponse getRecruitmentForm(Long id) {
		Recruitment recruitment = recruitmentGetService.getRecruitmentById(id);

		return recruitmentResponseMapper.toRecruitmentResponse(recruitment);
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
