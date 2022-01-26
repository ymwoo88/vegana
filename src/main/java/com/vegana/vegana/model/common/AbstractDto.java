package com.vegana.vegana.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Persistable;

public abstract class AbstractDto<ID, Entity, Dto> implements Persistable<ID> {

    public Dto of(final Entity entity) {
        return null;
    }

    public Entity toEntity() {
        return null;
    }

    @JsonIgnore
    @Override
    public boolean isNew()
    {
        return getId() == null;
    }
}
