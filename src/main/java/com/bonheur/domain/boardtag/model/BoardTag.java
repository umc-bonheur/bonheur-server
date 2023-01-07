package com.bonheur.domain.boardtag.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.tag.model.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class BoardTag extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Tag tag;
}
