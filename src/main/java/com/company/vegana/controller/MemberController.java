package com.company.vegana.controller;

import com.company.vegana.model.member.MemberDto;
import com.company.vegana.model.member.MemberSearch;
import com.company.vegana.service.MemberService;
import com.company.vegana.servlet.dto.ApiResponse;
import com.company.vegana.servlet.log.LogKey;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberDto>> create(@RequestBody MemberDto dto) {
        return ResponseEntity.ok(
                ApiResponse.<MemberDto>builder()
                        .logKey(LogKey.get())
                        .data(memberService.create(dto))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDto>> detail(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<MemberDto>builder()
                        .logKey(LogKey.get())
                        .data(memberService.detail(MemberDto.builder().id(id).build()))
                        .build()
        );
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<MemberDto>>> searchList(@ModelAttribute MemberSearch search) {
        return ResponseEntity.ok(
                ApiResponse.<List<MemberDto>>builder()
                        .logKey(LogKey.get())
                        .data(memberService.searchList(search))
                        .build()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<MemberDto>>> searchPage(@ModelAttribute MemberSearch search) {
        return ResponseEntity.ok(
                ApiResponse.<Page<MemberDto>>builder()
                        .logKey(LogKey.get())
                        .data(memberService.searchPage(search))
                        .build()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<MemberDto>> update(@RequestBody MemberDto dto) {
        return ResponseEntity.ok(
                ApiResponse.<MemberDto>builder()
                        .logKey(LogKey.get())
                        .data(memberService.update(dto))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .logKey(LogKey.get())
                        .data("delete success")
                        .build()
        );
    }
}
