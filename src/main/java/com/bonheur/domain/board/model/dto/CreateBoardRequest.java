package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateBoardRequest {
    private String contents;
    private Long member_id;
    private List<String> tags;
    @Builder
    private CreateBoardRequest(String contents, Long member_id, List<String> tags){
        this.contents = contents;
        this.member_id = member_id;
        this.tags = tags;
    }

    public Board toEntity(Member member){
        return Board.newBoard(contents, member);
    }
}
