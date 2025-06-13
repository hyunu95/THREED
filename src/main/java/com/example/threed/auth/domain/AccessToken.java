package com.example.threed.auth.domain;

import java.util.Date;

import com.example.threed.auth.config.AuthProperties;
import com.example.threed.common.exception.ThreedBadRequestException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

@Getter
public class AccessToken implements JwtToken {

	private final String value;

	// 멤버 ID와 AuthProperties로 새 액세스 토큰 생성
	public AccessToken(long memberId, AuthProperties authProperties) {
		Date validity = new Date(System.currentTimeMillis() + authProperties.getAccessExpiration());
		this.value = Jwts.builder()
			.setSubject(String.valueOf(memberId))
			.setExpiration(validity)
			.signWith(SignatureAlgorithm.HS256, authProperties.getAccessKey())
			.compact();
	}

	// 기존 토큰 문자열로 객체 생성 (Bearer 제거는 컨트롤러에서 완료됨)
	public AccessToken(String rawValue) {
		if (rawValue == null || rawValue.isBlank()) {
			throw new ThreedBadRequestException("잘못된 액세스 토큰 형식입니다.");
		}
		this.value = rawValue.trim();
	}

	@Override
	public String getSecretKey(AuthProperties authProperties) {
		return authProperties.getAccessKey();
	}
}
