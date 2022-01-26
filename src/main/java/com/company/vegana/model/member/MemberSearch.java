package com.company.vegana.model.member;

import com.company.vegana.model.common.AbstractSearch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class MemberSearch extends AbstractSearch {

    private Long id;

    private String email;

    private String password;

    private Member.Authority authority;

    private String name;

    private String nickname;

    private String profileUrl;

    private String phoneNo;

    private String dateOfBirth;
}
