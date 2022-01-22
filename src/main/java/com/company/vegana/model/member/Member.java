package com.company.vegana.model.member;

import com.company.vegana.model.common.AbstractAudit;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Table(name = "MEMBER")
@Entity
public class Member extends AbstractAudit<Member> {

    public enum Authority {
        ROLE_USER, ROLE_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_KEY")
    private Long id;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUTHORITY")
    private Authority authority;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PROFILE_URL")
    private String profileUrl;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @Builder
    protected Member(final String email,
                     final String password,
                     final Authority authority,
                     final String name,
                     final String nickname,
                     final String profileUrl,
                     final String phoneNo,
                     final String dateOfBirth) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.name = name;
        this.phoneNo = phoneNo;
        this.dateOfBirth = dateOfBirth;
    }

    public void update(final Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.authority = member.getAuthority();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.profileUrl = member.getProfileUrl();
        this.phoneNo = member.getPhoneNo();
        this.dateOfBirth = member.getDateOfBirth();
    }
}
