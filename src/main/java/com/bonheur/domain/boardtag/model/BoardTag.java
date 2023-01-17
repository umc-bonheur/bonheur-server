package com.bonheur.domain.boardtag.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.tag.model.Tag;
import lombok.AccessLevel;
import lombok.Builder;
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
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder(access = AccessLevel.PRIVATE)
    private BoardTag(Board board, Tag tag){
        this.board = board;
        this.tag = tag;
    }

    public static BoardTag newBoardTag(Board board, Tag tag){
        return BoardTag.builder()
                .board(board)
                .tag(tag)
                .build();
    }
}
