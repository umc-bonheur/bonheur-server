package com.bonheur.domain.tag.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTagResponse {
    private List<Long> tagsIds;

    public static CreateTagResponse of(@NotNull List<Long> tagsIds){
        return new CreateTagResponse(tagsIds);
    }
}