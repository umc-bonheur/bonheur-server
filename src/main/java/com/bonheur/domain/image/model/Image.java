package com.bonheur.domain.image.model;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.common.BaseEntity;
import lombok.AccessLevel;
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
    private String path;

    @Column(nullable = false)
    private Long sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder(access = AccessLevel.PRIVATE)
    private Image(String url, String path, Long sequence, Board board){
        this.url = url;
        this.path = path;
        this.sequence = sequence;
        this.board = board;
    }
    public static Image newImage(String url, String path, Long sequence, Board board){
        return Image.builder()
                .url(url)
                .path(path)
                .sequence(sequence)
                .board(board)
                .build();
    }
}
