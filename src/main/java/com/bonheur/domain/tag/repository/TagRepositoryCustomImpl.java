package com.bonheur.domain.tag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TagRepositoryCustomImpl implements TagRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
