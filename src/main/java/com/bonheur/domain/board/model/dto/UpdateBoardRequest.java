package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardRequest {
    private String contents;
    private List<String> tags;

    @Builder
    private UpdateBoardRequest(String contents, List<String> tags){
        this.contents = contents;
        this.tags = tags;
    }

    public Board toEntity(Member member){
        return Board.newBoard(contents, member);
    }
}
