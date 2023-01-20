package com.bonheur.domain.board.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardResponse {
    private Long boardId;
    private String contents;
    private List<String> images;
    private List<String> tags;

    public static GetBoardResponse of(@NotNull Long boardId, String contents, List<String> images, List<String> tags) {
        return new GetBoardResponse(boardId, contents, images, tags);
    }
}