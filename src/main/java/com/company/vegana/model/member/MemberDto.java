package com.company.vegana.model.member;

import com.company.vegana.model.common.AbstractDto;
import lombok.*;

import javax.persistence.Column;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class MemberDto extends AbstractDto<Long, Member, MemberDto> {

    private Long id;

    private String email;

    private String password;

    private Member.Authority authority;

    private String name;

    private String nickname;

    private String profileUrl;

    private String phoneNo;

    private String dateOfBirth;

    public MemberDto of(final Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .authority(member.getAuthority())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .phoneNo(member.getPhoneNo())
                .dateOfBirth(member.getDateOfBirth())
                .build();

    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .phoneNo(phoneNo)
                .dateOfBirth(dateOfBirth)
                .build();
    }
}
