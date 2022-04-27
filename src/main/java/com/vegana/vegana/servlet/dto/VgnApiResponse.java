package com.vegana.vegana.servlet.dto;

import com.vegana.vegana.servlet.log.LogKey;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class VgnApiResponse<T> {

    private String logKey;
    private T data;

    public VgnApiResponse(T data) {
        this.logKey = LogKey.get();
        this.data = data;
    }
}
