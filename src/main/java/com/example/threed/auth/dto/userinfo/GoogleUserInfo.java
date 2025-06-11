package com.example.threed.auth.dto.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GoogleUserInfo implements OAuthUserInfo {
	private String id;
	private String email;
	private String nickname;
	private String profileImageUrl;

	@Override
	public String getProviderId() {
		return id;
	}
	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public String getNickname() {
		return nickname;
	}
	@Override
	public String getProfileImageUrl() {
		return profileImageUrl;
	}
}
