package com.company.vegana.model.member;

import com.company.vegana.model.common.AbstractSearch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearch extends AbstractSearch {

    private String nickname;

    private String email;
}
