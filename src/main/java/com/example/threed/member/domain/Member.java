package com.example.threed.member.domain;

import com.example.threed.auth.domain.RefreshToken;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String profileImageUrl;

	@Embedded
	private AuthProvider authProvider;

	@Embedded
	private RefreshToken refreshToken;

	private LocalDateTime deletedAt;

	public Member(
		String nickname,
		String email,
		String profileImageUrl,
		AuthProvider authProvider,
		RefreshToken refreshToken
	) {
		this.nickname = nickname;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.authProvider = authProvider;
		this.refreshToken = refreshToken;
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}
}
