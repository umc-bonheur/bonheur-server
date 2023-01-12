package com.bonheur.domain.board.model;

import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.common.BaseEntity;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Column(length = 3000)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Board(String contents, Member member){
        this.contents = contents;
        this.member = member;
    }
}
