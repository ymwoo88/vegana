package com.vegana.vegana.service.common;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<ID, Dto, Search> {

    Dto create(Dto dto);

    Dto detail(Dto dto);

    List<Dto> searchList(Search search);

    Page<Dto> searchPage(Search search);

    Dto update(Dto dto);

    void delete(ID id);
}
