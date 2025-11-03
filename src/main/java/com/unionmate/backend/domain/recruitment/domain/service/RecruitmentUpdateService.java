package com.unionmate.backend.domain.recruitment.domain.service;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.UpdateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUpdateService {
	public void updateRecruitment(Recruitment recruitment, UpdateRecruitmentRequest updateRecruitmentRequest) {
		recruitment.updateName(updateRecruitmentRequest.name());
		recruitment.updateEndAt(updateRecruitmentRequest.endAt());
		recruitment.updateIsActive(updateRecruitmentRequest.isActive());
		recruitment.updateStatus(updateRecruitmentRequest.recruitmentStatus());
	}

	public void updateCommonItem(Item item, UpdateItemRequest updateItemRequest) {
		item.updateRequired(updateItemRequest.required());
		item.updateTitle(updateItemRequest.title());
		item.updateOrder(updateItemRequest.order());
		item.updateDescription(updateItemRequest.description());
	}
}
