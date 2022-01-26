package com.vegana.vegana.model.member;

import com.vegana.vegana.model.common.AbstractDto;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class MemberDto extends AbstractDto<Long, Member, MemberDto> {

    private Long id;

    private String email;

    private String name;

    private String nickname;

    private String profileUrl;

    public MemberDto of(final Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .profileUrl(member.getProfileUrl())
                .build();

    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .profileUrl(profileUrl)
                .build();
    }
}
