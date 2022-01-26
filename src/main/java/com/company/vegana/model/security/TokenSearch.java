package com.company.vegana.model.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenSearch {

    private String accessToken;

    private String refreshToken;
}
