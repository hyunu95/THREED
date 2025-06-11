package com.example.threed.auth.dto.userinfo;

public interface OAuthUserInfo {
	String getProviderId();
	String getEmail();
	String getNickname();
	String getProfileImageUrl();
}
