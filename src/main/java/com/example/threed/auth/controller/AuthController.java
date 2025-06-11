package com.example.threed.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.threed.auth.dto.TokenResponse;
import com.example.threed.auth.service.OAuthLoginService;
import com.example.threed.member.domain.ProviderType;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final OAuthLoginService oAuthLoginService;

	@GetMapping("/google/callback")
	public ResponseEntity<TokenResponse> googleCallback(@RequestParam("code") String code,
		HttpServletResponse response) {
		TokenResponse tokenResponse = oAuthLoginService.login(ProviderType.GOOGLE, code, response);
		return ResponseEntity.ok(tokenResponse);
	}
}
