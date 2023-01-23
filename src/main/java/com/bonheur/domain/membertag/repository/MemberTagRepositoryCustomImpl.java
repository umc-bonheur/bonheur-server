package com.bonheur.domain.membertag.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberTagRepositoryCustomImpl implements MemberTagRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
}
