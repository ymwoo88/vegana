package com.vegana.vegana.repository.member;

import com.vegana.vegana.model.member.Member;
import com.vegana.vegana.model.member.MemberDto;
import com.vegana.vegana.model.member.MemberSearch;
import com.vegana.vegana.repository.common.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, SearchRepository<MemberDto, MemberSearch> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
