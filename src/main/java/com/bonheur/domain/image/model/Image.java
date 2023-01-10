package com.bonheur.domain.image.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class Image extends BaseEntity {
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
