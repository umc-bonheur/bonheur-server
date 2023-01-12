package com.bonheur.domain.board.model.dto;

import com.bonheur.domain.board.model.Board;
import com.bonheur.domain.member.model.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBoardRequest {
    private String contents;
    private Long member_id; //jwtService 추가시 제거

    @Builder
    public CreateBoardRequest(String contents, Long member_id){
        this.contents = contents;
        this.member_id = member_id; //jwtService 추가시 제거
    }

    public Board toEntity(Member member){
        return Board.builder()
                .contents(contents)
                .member(member)
                .build();
    }
}
