package com.company.vegana.repository.member;

import com.company.vegana.model.member.Member;
import com.company.vegana.model.member.MemberDto;
import com.company.vegana.model.member.MemberSearch;
import com.company.vegana.repository.common.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, SearchRepository<MemberDto, MemberSearch> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
