package com.vegana.vegana.controller.security;

import com.vegana.vegana.model.security.TokenDto;
import com.vegana.vegana.model.security.TokenSearch;
import com.vegana.vegana.model.member.MemberDto;
import com.vegana.vegana.model.member.MemberSearch;
import com.vegana.vegana.service.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDto> signup(@RequestBody MemberSearch memberSearch) {
        return ResponseEntity.ok(authService.signup(memberSearch));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberSearch memberSearch) {
        return ResponseEntity.ok(authService.login(memberSearch));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenSearch tokenSearch) {
        return ResponseEntity.ok(authService.reissue(tokenSearch));
    }
}