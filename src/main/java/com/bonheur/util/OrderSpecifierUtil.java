package com.bonheur.util;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Component;

import static com.bonheur.domain.board.model.QBoard.board;

@Component
public class OrderSpecifierUtil {
    public static OrderSpecifier<?> getOrderSpecifier(String orderType) {
        OrderSpecifier<?> orderSpecifier = orderType.equals("newest") ? board.createdAt.desc()
                : board.createdAt.asc();

        return orderSpecifier;
    }
}