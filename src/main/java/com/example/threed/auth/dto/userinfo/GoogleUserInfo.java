package com.example.threed.auth.dto.userinfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfo implements OAuthUserInfo {
	private String id;
	private String email;

	@JsonProperty("name")
	private String name;

	@JsonProperty("picture")
	private String picture;

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
		return name;
	}
	@Override
	public String getProfileImageUrl() {
		return picture;
	}
}
