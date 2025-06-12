package com.example.threed.member.repository;

import java.util.Optional;
import com.example.threed.member.domain.Member;
import com.example.threed.member.domain.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByAuthProviderProviderTypeAndAuthProviderProviderId(ProviderType providerType, String providerId);
	Optional<Member> findByRefreshToken_Value(String refreshTokenValue);
}
