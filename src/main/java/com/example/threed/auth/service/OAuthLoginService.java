package com.example.threed.auth.service;

import com.example.threed.auth.domain.AccessToken;
import com.example.threed.auth.domain.RefreshToken;
import com.example.threed.auth.dto.TokenResponse;
import com.example.threed.auth.dto.userinfo.OAuthUserInfo;
import com.example.threed.auth.service.client.OAuthClient;
import com.example.threed.member.domain.Member;
import com.example.threed.member.domain.ProviderType;
import com.example.threed.member.service.MemberService;
import com.example.threed.common.exception.ThreedBadRequestException;
import com.example.threed.common.exception.ThreedNotFoundException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

	private final Map<String, OAuthClient> oauthClients;
	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public TokenResponse login(ProviderType providerType, String code, HttpServletResponse response) {
		OAuthClient oAuthClient = oauthClients.get(providerType.name());
		if (oAuthClient == null) {
			throw new ThreedBadRequestException("지원하지 않는 소셜 로그인입니다: " + providerType.name());
		}

		String oauthAccessToken;
		OAuthUserInfo userInfo;
		try {
			oauthAccessToken = oAuthClient.requestAccessToken(code);
			userInfo = oAuthClient.requestUserInfo(oauthAccessToken);
		} catch (Exception e) {
			throw new ThreedBadRequestException("소셜 로그인 처리 중 오류가 발생했습니다.");
		}

		Member member;
		try {
			member = memberService.findOrCreate(
				providerType,
				userInfo.getProviderId(),
				userInfo.getEmail(),
				userInfo.getNickname(),
				userInfo.getProfileImageUrl()
			);
		} catch (Exception e) {
			throw new ThreedNotFoundException("회원 정보를 찾거나 생성하는데 실패했습니다.");
		}

		AccessToken accessToken = jwtTokenProvider.createAccessToken(member.getId());
		RefreshToken refreshToken = jwtTokenProvider.createRefreshToken();

		memberService.updateRefreshToken(member, refreshToken);

		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getValue());
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge((int) (jwtTokenProvider.getAuthProperties().getRefreshExpiration() / 1000));
		response.addCookie(refreshTokenCookie);

		return new TokenResponse(
			accessToken.getValue(),
			member.getNickname(),
			member.getEmail(),
			member.getProfileImageUrl()
		);
	}
}
