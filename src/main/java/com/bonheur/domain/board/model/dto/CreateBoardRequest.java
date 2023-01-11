package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CreateBoardRequest {
    private String contents;
    private Member member;
    private List<Image> images = new ArrayList<>();
    private List<BoardTag> boardTags = new ArrayList<>();

    @Builder
    public CreateBoardRequest(String contents, Member member){
        this.contents = contents;
        this.member = member;
    }

    //dto -> entity
    public Board toEntity() {
        return Board.builder()
                .contents(contents)
                .member(member)
                .build();
    }
}
