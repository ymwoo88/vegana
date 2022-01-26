package com.company.vegana.service.security;

import com.company.vegana.model.security.RefreshToken;
import com.company.vegana.model.security.TokenDto;
import com.company.vegana.config.security.TokenProvider;
import com.company.vegana.model.security.TokenSearch;
import com.company.vegana.model.member.Member;
import com.company.vegana.model.member.MemberDto;
import com.company.vegana.model.member.MemberSearch;
import com.company.vegana.repository.security.RefreshTokenRepository;
import com.company.vegana.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberDto signup(MemberSearch memberSearch) {
        if (memberRepository.existsByEmail(memberSearch.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = Member.builder()
                .email(memberSearch.getEmail())
                .password(passwordEncoder.encode(memberSearch.getPassword()))
                .authority(memberSearch.getAuthority())
                .nickname(memberSearch.getNickname())
                .profileUrl(memberSearch.getProfileUrl())
                .name(memberSearch.getName())
                .phoneNo(memberSearch.getPhoneNo())
                .dateOfBirth(memberSearch.getDateOfBirth())
                .build();

        return MemberDto.builder().build().of(memberRepository.save(member));
    }

    @Transactional
    public TokenDto login(MemberSearch memberSearch) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        memberSearch.getEmail(),
                        memberSearch.getPassword(),
                        List.of(new SimpleGrantedAuthority(memberSearch.getAuthority().toString()))
                );

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenSearch tokenSearch) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenSearch.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenSearch.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenSearch.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}
