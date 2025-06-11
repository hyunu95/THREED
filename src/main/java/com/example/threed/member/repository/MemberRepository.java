package com.example.threed.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.threed.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
