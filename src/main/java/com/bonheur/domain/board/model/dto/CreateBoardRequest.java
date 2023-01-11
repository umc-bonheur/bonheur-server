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
    private Long member_id; //jwtService 추가시 제거
    private List<Image> images = new ArrayList<>();
    private List<BoardTag> boardTags = new ArrayList<>();

    @Builder
    public CreateBoardRequest(String contents, Long member_id, List<Image> images, List<BoardTag> boardTags){
        this.contents = contents;
        this.member_id = member_id; //jwtService 추가시 제거
        this.images = images;
        this.boardTags = boardTags;
    }

    public Board toEntity(Member member){
        return Board.builder()
                .contents(contents)
                .member(member)
                .build();
    }
}
