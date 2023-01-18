package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardRequest {
    private String contents;

    @Length(max = 10)
    private List<String> tags;

    @Builder
    private CreateBoardRequest(String contents, List<String> tags){
        this.contents = contents;
        this.tags = tags;
    }

    public Board toEntity(Member member){
        return Board.newBoard(contents, member);
    }
}
