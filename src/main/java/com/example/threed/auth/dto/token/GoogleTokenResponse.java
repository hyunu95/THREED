package com.example.threed.auth.dto.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleTokenResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private int expiresIn;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("refresh_token")
	private String refreshToken;

	// 액세스 토큰 getter
	public String accessToken() {
		return accessToken;
	}
}
