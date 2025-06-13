package com.example.threed.auth.controller;

import com.example.threed.auth.service.AuthService;
import com.example.threed.member.domain.Member;
import com.example.threed.member.dto.response.UserResponse;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthInfoController {

	private final AuthService authService;

	// 엑세스토큰으로 내 정보 조회
	@GetMapping("/me")
	public ResponseEntity<UserResponse> me(
		@RequestHeader(value = "Authorization", required = false) String authorization) {
		if (authorization == null || authorization.isBlank()) {
			return ResponseEntity.status(401).build();
		}
		String token = authorization.replace("Bearer ", "").trim();
		try {
			Member member = authService.parseAccessToken(token);
			return ResponseEntity.ok(new UserResponse(member));
		} catch (Exception e) {
			System.out.println("Invalid access token: " + e.getMessage());
			return ResponseEntity.status(401).body(null);
		}
	}


	// 리프레시토큰으로 엑세스토큰 재발급
	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(
		@CookieValue(value = "refreshToken", required = false) String refreshToken
	) {
		if (refreshToken == null) {
			return ResponseEntity.status(401).body(Map.of("error", "리프레시 토큰이 없습니다."));
		}
		// refreshToken을 검증해서 새로운 accessToken 발급
		String newAccessToken = authService.refreshAccessToken(refreshToken);
		return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}
}
