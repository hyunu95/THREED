package com.example.threed.member.dto.response;

import com.example.threed.member.domain.Member;
import lombok.Getter;

@Getter
public class UserResponse {
	private final Long id;
	private final String nickname;
	private final String email;
	private final String profileImageUrl;

	public UserResponse(Member member) {
		this.id = member.getId();
		this.nickname = member.getNickname();
		this.email = member.getEmail();
		this.profileImageUrl = member.getProfileImageUrl();
	}
}
