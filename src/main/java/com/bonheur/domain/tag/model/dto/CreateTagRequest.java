package com.bonheur.domain.tag.model.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTagRequest {

    @Valid
    private List<tagList> tags;

    @Data
    public static class tagList{
        @NotBlank
        @Size(max = 10)
        private String tag;
    }

    public static CreateTagRequest of(List<tagList> tags){
        return new CreateTagRequest(tags);
    }
}

