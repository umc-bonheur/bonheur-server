package com.bonheur.domain.board.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardRequest {

    @NotBlank
    private String contents;

    private List<Long> tagIds;
}