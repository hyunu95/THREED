package com.example.threed.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.threed.member.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthLogoutController {

	private final MemberRepository memberRepository;

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = null;
		if (request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if ("refreshToken".equals(cookie.getName())) {
					refreshToken = cookie.getValue();
					break;
				}
			}
		}

		if (refreshToken != null) {
			memberRepository.findByRefreshToken_Value(refreshToken).ifPresent(member -> {
				member.setRefreshToken(null);
				memberRepository.save(member);
			});
		}

		Cookie expiredCookie = new Cookie("refreshToken", null);
		expiredCookie.setHttpOnly(true);
		expiredCookie.setPath("/");
		expiredCookie.setMaxAge(0);
		response.addCookie(expiredCookie);

		return ResponseEntity.noContent().build();
	}
}
