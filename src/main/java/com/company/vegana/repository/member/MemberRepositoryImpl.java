package com.company.vegana.repository.member;

import com.company.vegana.model.member.Member;
import com.company.vegana.model.member.MemberDto;
import com.company.vegana.model.member.MemberSearch;
import com.company.vegana.repository.common.SearchRepository;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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

import static com.company.vegana.model.member.QMember.member;

@Transactional(readOnly=true)
@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements SearchRepository<MemberDto, MemberSearch> {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberDto> searchList(MemberSearch search) {
        return queryFactory.selectFrom(member)
                .where(member.email.eq(search.getEmail()))
                .fetch()
                .stream()
                .map(resource -> MemberDto.builder().build()
                        .of(resource))
                .collect(Collectors.toList());
    }

    @Override
    public Page<MemberDto> searchPage(MemberSearch search) {
        JPQLQuery<Member> query = this.queryFactory
                .selectFrom(member)
                .where(member.email.eq(search.getEmail()));

        long totalCount = query.fetchCount();

        Pageable pageable = search.getPageable();
        extractedSort(query, pageable);

        List<MemberDto> results = query.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch()
                .stream().map(resource -> MemberDto.builder().build()
                        .of(resource))
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, totalCount);
    }

    private void extractedSort(JPQLQuery<Member> query, Pageable pageable)
    {
        for ( Sort.Order o : pageable.getSort() )
        {
            PathBuilder<Member> pathBuilder = new PathBuilder<>(member.getType(), member.getMetadata());
            query.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }
    }
}
