package com.example.threed.auth.service;

import org.springframework.stereotype.Service;

import com.example.threed.auth.domain.AccessToken;
import com.example.threed.auth.domain.RefreshToken;
import com.example.threed.auth.domain.JwtToken;
import com.example.threed.member.domain.Member;
import com.example.threed.member.repository.MemberRepository;
import com.example.threed.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final MemberService memberService;
	private final MemberRepository memberRepository;

	public Member parseAccessToken(String rawAccessToken) {
		AccessToken accessToken = new AccessToken(rawAccessToken);
		jwtTokenProvider.validate(accessToken);
		long memberId = jwtTokenProvider.parseAccessToken(accessToken);

		return memberService.findById(memberId);
	}

	public String refreshAccessToken(String refreshToken) {
		// 1. 토큰 객체 생성 및 검증
		RefreshToken refreshTokenObj = new RefreshToken(refreshToken);
		jwtTokenProvider.validate(refreshTokenObj);

		// 2. 해당 리프레시 토큰을 가진 멤버를 직접 레포지토리로 조회
		Member member = memberRepository.findByRefreshToken_Value(refreshToken)
			.orElse(null);
		if (member == null) {
			throw new RuntimeException("유효하지 않은 리프레시 토큰");
		}

		// 3. 새 accessToken 발급
		AccessToken newAccessToken = jwtTokenProvider.createAccessToken(member.getId());
		return newAccessToken.getValue();
	}
}
