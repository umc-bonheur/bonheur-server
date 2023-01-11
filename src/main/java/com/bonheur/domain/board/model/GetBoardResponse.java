package com.bonheur.domain.board.model;

import com.bonheur.domain.boardtag.model.BoardTag;
import com.bonheur.domain.image.model.Image;
import com.bonheur.domain.member.model.Member;
import lombok.Getter;

import java.util.List;

@Getter
public class GetBoardResponse {
    private final String contents;
    private final Member member;
    private final List<BoardTag> boardTags;
    private final List<Image> images;

    // 생성자 오버로딩
    public GetBoardResponse(Board board) {
        this.contents = board.getContents();
        this.member = board.getMember();
        this.boardTags = board.getBoardTags();
        this.images = board.getImages();
    }
}