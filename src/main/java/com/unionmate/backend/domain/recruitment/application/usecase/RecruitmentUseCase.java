package com.unionmate.backend.domain.recruitment.application.usecase;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.unionmate.backend.domain.recruitment.application.dto.request.CreateItemRequest;
import com.unionmate.backend.domain.recruitment.application.dto.request.CreateRecruitmentRequest;
import com.unionmate.backend.domain.recruitment.application.mapper.RecruitmentMapper;
import com.unionmate.backend.domain.recruitment.domain.entity.Recruitment;
import com.unionmate.backend.domain.recruitment.domain.entity.item.Item;
import com.unionmate.backend.domain.recruitment.domain.service.RecruitmentSaveService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentUseCase {
	private final RecruitmentMapper recruitmentMapper;
	private final RecruitmentSaveService recruitmentSaveService;

	public void createRecruitment(CreateRecruitmentRequest rq) {
		Recruitment recruitment = recruitmentMapper
			.toRecruitment(rq.name(), LocalDateTime.now(), rq.endAt(), rq.isActive(), rq.recruitmentStatus());

		if (rq.items() != null) {
			for (CreateItemRequest ir : rq.items()) {
				Item item = recruitmentMapper.toItem(recruitment, ir);
				recruitment.getItems().add(item);
			}
		}
		recruitmentSaveService.save(recruitment);
	}
}
