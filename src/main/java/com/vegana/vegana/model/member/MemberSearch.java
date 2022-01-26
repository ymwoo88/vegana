package com.vegana.vegana.model.member;

import com.vegana.vegana.model.common.AbstractSearch;
import lombok.Getter;
import lombok.Setter;

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
