package com.company.vegana.model.user;

import com.company.vegana.model.common.AbstractSearch;
import lombok.*;

@Getter
@Setter
public class UserSearch extends AbstractSearch {

    private String name;

    private String email;
}
