package com.bonheur.domain.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
}
