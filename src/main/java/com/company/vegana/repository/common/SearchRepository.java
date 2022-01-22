package com.company.vegana.repository.common;

import com.company.vegana.model.common.AbstractSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SearchRepository<Dto, Search extends AbstractSearch> {

    List<Dto> searchList(Search search);

    Page<Dto> searchPage(Search search);
}
