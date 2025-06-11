package com.example.threed.auth.service.client;

import com.example.threed.auth.dto.userinfo.OAuthUserInfo;

public interface OAuthClient {
	String requestAccessToken(String code);
	OAuthUserInfo requestUserInfo(String accessToken);
}
