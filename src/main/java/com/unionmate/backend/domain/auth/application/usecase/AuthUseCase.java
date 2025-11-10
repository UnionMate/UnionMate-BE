package com.unionmate.backend.domain.auth.application.usecase;

import com.unionmate.backend.domain.auth.application.dto.request.ManagerLoginRequest;
import com.unionmate.backend.domain.auth.application.dto.request.ManagerRegisterRequest;
import com.unionmate.backend.domain.auth.application.dto.response.ManagerLoginResponse;
import com.unionmate.backend.domain.auth.application.dto.response.ManagerRegisterResponse;
import com.unionmate.backend.domain.auth.application.dto.response.ReissueResponse;
import com.unionmate.backend.domain.auth.domain.service.AuthService;
import com.unionmate.backend.domain.auth.exception.EmailDuplicateException;
import com.unionmate.backend.domain.auth.exception.PasswordNotMatchException;
import com.unionmate.backend.domain.auth.exception.TokenIssuanceException;
import com.unionmate.backend.domain.council.domain.entity.CouncilManager;
import com.unionmate.backend.domain.council.domain.service.CouncilManagerGetService;
import com.unionmate.backend.domain.member.domain.entity.Member;
import com.unionmate.backend.domain.member.domain.service.MemberGetService;
import com.unionmate.backend.domain.member.domain.service.MemberSaveService;
import com.unionmate.backend.global.kafka.event.JwtGenerateEvent;
import com.unionmate.backend.global.kafka.event.JwtTokenEvent;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUseCase {

	private final AuthService authService;
	private final MemberGetService memberGetService;
	private final MemberSaveService memberSaveService;
	private final CouncilManagerGetService councilManagerGetService;
	private final ReplyingKafkaTemplate<String, JwtGenerateEvent, JwtTokenEvent> jwtGenerateReplyingKafkaTemplate;

	@Value("${kafka.topics.jwt-generate-request}")
	private String jwtGenerateRequestTopic;

	@Value("${kafka.timeout-ms}")
	private long timeoutMs;

	@Transactional
	public ManagerRegisterResponse managerRegister(ManagerRegisterRequest managerRegisterRequest) {
		if (this.memberGetService.existsByEmail(managerRegisterRequest.email())) {
			throw new EmailDuplicateException();
		}

		// TODO: 학교 이메일 인증

		String encodePassword = this.authService.encodePassword(managerRegisterRequest.password());

		Member member = Member.builder()
			.name(managerRegisterRequest.name())
			.email(managerRegisterRequest.email())
			.password(encodePassword)
			.build();

		Member persisted = this.memberSaveService.save(member);

		JwtTokenEvent tokenEvent = issueTokens(persisted);

		return ManagerRegisterResponse.of(tokenEvent.getAccessToken(), tokenEvent.getRefreshToken());
	}

	public ManagerLoginResponse managerLogin(ManagerLoginRequest managerLoginRequest) {
		Member member = this.memberGetService.getMemberByEmail(managerLoginRequest.email());

		if (!this.authService.isValidPassword(managerLoginRequest.password(), member.getPassword())) {
			throw new PasswordNotMatchException();
		}

		JwtTokenEvent tokenEvent = issueTokens(member);

		Long councilId = null;
		if (councilManagerGetService.existsByMember(member)) {
			CouncilManager councilManager = councilManagerGetService.getCouncilManagerByMemberId(member.getId());
			councilId = councilManager.getCouncil().getId();
		}

		return ManagerLoginResponse.of(tokenEvent.getAccessToken(), tokenEvent.getRefreshToken(), councilId);
	}

	public ReissueResponse reissue(Long memberId) {
		Member member = this.memberGetService.getMemberById(memberId);

		JwtTokenEvent tokenEvent = issueTokens(member);

		return ReissueResponse.of(tokenEvent.getAccessToken(), tokenEvent.getRefreshToken());
	}

	private JwtTokenEvent issueTokens(Member member) {
		try {
			String eventId = UUID.randomUUID().toString();

			JwtGenerateEvent generateEvent = JwtGenerateEvent.builder()
					.eventId(eventId)
					.userId(member.getId())
					.email(member.getEmail())
					.name(member.getName())
					.build();

			ProducerRecord<String, JwtGenerateEvent> record =
					new ProducerRecord<>(jwtGenerateRequestTopic, eventId, generateEvent);

			RequestReplyFuture<String, JwtGenerateEvent, JwtTokenEvent> replyFuture =
					jwtGenerateReplyingKafkaTemplate.sendAndReceive(record, Duration.ofMillis(timeoutMs));

			JwtTokenEvent tokenEvent = replyFuture.get().value();

			if (tokenEvent == null) {
				throw new TokenIssuanceException();
			}

			if (tokenEvent.getAccessToken() == null || tokenEvent.getRefreshToken() == null) {
				throw new TokenIssuanceException();
			}

			return tokenEvent;

		} catch (TokenIssuanceException e) {
			throw e;
		} catch (Exception e) {
			throw new TokenIssuanceException();
		}
	}
}
