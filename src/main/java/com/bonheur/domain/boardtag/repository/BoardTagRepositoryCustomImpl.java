package com.bonheur.domain.boardtag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardTagRepositoryCustomImpl implements BoardTagRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
}
