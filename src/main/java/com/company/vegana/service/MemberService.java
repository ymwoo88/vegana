package com.company.vegana.service;

import com.company.vegana.model.member.Member;
import com.company.vegana.model.member.MemberDto;
import com.company.vegana.model.member.MemberSearch;
import com.company.vegana.repository.member.MemberRepository;
import com.company.vegana.service.common.AbstractService;
import com.company.vegana.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService extends AbstractService<Long, Member, MemberDto, MemberSearch, MemberRepository> {

    private final MemberRepository memberRepository;

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    public MemberDto getMyInfo() {
        return Optional.ofNullable(super.detail(MemberDto.builder()
                        .id(SecurityUtil.getCurrentMemberId())
                        .build()))
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }

    public MemberDto getMemberInfo(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> MemberDto.builder().build().of(member))
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }
}
