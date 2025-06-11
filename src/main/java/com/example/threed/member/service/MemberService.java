package com.example.threed.member.service;

import com.example.threed.common.exception.ThreedNotFoundException;
import com.example.threed.common.exception.ThreedUnauthorizedException;
import com.example.threed.member.domain.Member;
import com.example.threed.member.domain.ProviderType;
import com.example.threed.member.domain.AuthProvider;
import com.example.threed.auth.domain.RefreshToken;
import com.example.threed.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;

	public Member findById(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new ThreedNotFoundException("존재하지 않는 회원입니다."));

		if (member.isDeleted()) {
			throw new ThreedUnauthorizedException("탈퇴한 회원입니다.");
		}

		return member;
	}

	// 소셜 회원 조회/없으면 생성
	public Member findOrCreate(ProviderType providerType, String providerId, String email, String nickname, String profileImageUrl) {
		// AuthProvider로 회원 찾기
		Member member = memberRepository.findByAuthProviderProviderTypeAndAuthProviderProviderId(providerType, providerId)
			.orElse(null);

		// 이미 있으면 그대로 리턴
		if (member != null && !member.isDeleted()) {
			return member;
		}

		// 없으면 새로 생성
		AuthProvider authProvider = new AuthProvider(providerType, providerId);

		Member newMember = new Member(
			nickname,
			email,
			profileImageUrl,
			authProvider,
			null  // 리프레시 토큰은 로그인에서 저장
		);

		return memberRepository.save(newMember);
	}

	// 리프레시 토큰 저장/업데이트
	public void updateRefreshToken(Member member, RefreshToken refreshToken) {
		member.setRefreshToken(refreshToken);
		memberRepository.save(member);
	}
}
