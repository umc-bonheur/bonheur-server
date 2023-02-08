package com.bonheur.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardByTagRequest {
    @NotBlank
    private List<Long> tagIds;
}
