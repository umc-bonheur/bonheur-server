package com.bonheur.domain.board.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBoardRequest {

    @NotBlank
    @Size(max = 3000)
    private String contents;

    private List<Long> tagIds;
}