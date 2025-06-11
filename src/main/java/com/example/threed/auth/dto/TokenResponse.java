package com.example.threed.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {
	private String accessToken;
	private String nickname;
	private String email;
	private String profileImageUrl;
}
