package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.SelectOptionRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.AnnouncementItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.CalendarItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItem;
import com.unionmate.backend.domain.recruitment.domain.entity.item.SelectItemOption;
import com.unionmate.backend.domain.recruitment.domain.entity.item.TextItem;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentSaveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUseCase {
	private final RecruitmentSaveService recruitmentSaveService;

	public void createRecruitment(CreateRecruitmentRequest recruitmentRequest) {
		Recruitment recruitment = Recruitment.builder()
			.name(recruitmentRequest.name())
			.startAt(LocalDateTime.now())
			.endAt(recruitmentRequest.endAt())
			.isActive(recruitmentRequest.isActive())
			.recruitmentStatus(recruitmentRequest.recruitmentStatus())
			.build();

		if (recruitmentRequest.items() != null) {
			for (CreateItemRequest itemRequest : recruitmentRequest.items()) {
				Item item = toItemEntity(recruitment, itemRequest);
				recruitment.getItems().add(item);
			}
		}
		recruitmentSaveService.save(recruitment);
	}

	private Item toItemEntity(Recruitment recruitment, CreateItemRequest itemRequest) {
		boolean required = Boolean.TRUE.equals(itemRequest.required());
		String title = itemRequest.title();
		Integer order = itemRequest.order();
		String description = itemRequest.description();

		return switch (itemRequest.type()) {
			case TEXT -> TextItem.builder()
				.recruitment(recruitment)
				.required(required)
				.title(title)
				.order(order)
				.description(description)
				.text(itemRequest.text())
				.build();

			case SELECT -> {
				SelectItem selectItem = SelectItem.builder()
					.recruitment(recruitment)
					.required(required)
					.title(title)
					.order(order)
					.description(description)
					.multiple(Boolean.TRUE.equals(itemRequest.multiple()))
					.build();

				if(itemRequest.options() != null) {
					for(SelectOptionRequest optionRequest : itemRequest.options()) {
						selectItem.getSelectItemOptions().add(SelectItemOption.builder()
							.title(optionRequest.title())
							.order(optionRequest.order())
							.isEtc(Boolean.TRUE.equals(optionRequest.isEtc()))
							.etcTitle(optionRequest.title())
							.selectItem(selectItem)
							.build());
					}
				}
				yield selectItem;
			}

			case CALENDAR ->  CalendarItem.builder()
				.recruitment(recruitment)
				.required(required)
				.title(title)
				.order(order)
				.description(description)
				.date(itemRequest.date())
				.build();

			case ANNOUNCEMENT ->   AnnouncementItem.builder()
				.recruitment(recruitment)
				.required(required)
				.title(title)
				.order(order)
				.description(description)
				.announcement(itemRequest.announcement())
				.build();
		};
	}
}
