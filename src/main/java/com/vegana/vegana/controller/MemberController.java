package com.vegana.vegana.controller;

import com.vegana.vegana.model.member.MemberDto;
import com.vegana.vegana.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public MemberDto getMyMemberInfo() {
        return memberService.getMyInfo();
    }

    @GetMapping("/{email}")
    public MemberDto getMemberInfo(@PathVariable String email) {
        return memberService.getMemberInfo(email);
    }
}
