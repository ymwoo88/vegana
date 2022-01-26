package com.vegana.vegana.model.user;

import com.vegana.vegana.model.common.AbstractSearch;
import lombok.*;

@Getter
@Setter
public class UserSearch extends AbstractSearch {

    private String name;

    private String email;
}
