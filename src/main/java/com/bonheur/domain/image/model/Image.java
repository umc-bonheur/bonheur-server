package com.bonheur.domain.image.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.member.model.Member;
import lombok.Builder;
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

    public void saveImage(Board board){
        this.board = board;
         if(!board.getImages().contains(this)){
             board.getImages().add(this);
         }
    }   //게시글에 이미지 저장.
}
