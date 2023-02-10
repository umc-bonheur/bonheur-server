package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardRequest {
    @NotBlank
    @Size(max = 3000)
    private String contents;

    private List<Long> tagIds;

    public Board toEntity(Member member){
        return Board.newBoard(contents, member);
    }
}
