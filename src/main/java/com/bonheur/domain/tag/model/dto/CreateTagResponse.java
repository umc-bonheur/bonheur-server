package com.bonheur.domain.tag.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTagResponse {
    private List<Long> tagIds;

    public static CreateTagResponse of(@NotNull List<Long> tagIds){
        return new CreateTagResponse(tagIds);
    }
}