package com.company.vegana.repository.user;

import com.company.vegana.model.user.User;
import com.company.vegana.model.user.UserDto;
import com.company.vegana.model.user.UserSearch;
import com.company.vegana.repository.common.SearchRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.company.vegana.model.user.QUser.user;

@Transactional(readOnly=true)
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements SearchRepository<UserDto, UserSearch> {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserDto> searchList(UserSearch search) {
        return queryFactory.selectFrom(user)
                .where(user.name.eq(search.getName()))
                .fetch()
                .stream()
                .map(resource -> UserDto.builder().build()
                        .of(resource))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> searchPage(UserSearch search) {
        JPQLQuery<User> query = this.queryFactory
                .selectFrom(user)
                .where(user.name.eq(search.getName()));

        long totalCount = query.fetchCount();

        Pageable pageable = search.getPageable();
        extractedSort(query, pageable);

        List<UserDto> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch()
                .stream().map(resource -> UserDto.builder().build()
                        .of(resource))
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, totalCount);
    }

    private void extractedSort(JPQLQuery<User> query, Pageable pageable)
    {
        for ( Sort.Order o : pageable.getSort() )
        {
            PathBuilder<User> pathBuilder = new PathBuilder<>(user.getType(), user.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }
}
