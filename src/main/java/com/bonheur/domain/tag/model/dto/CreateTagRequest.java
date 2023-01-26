package com.bonheur.domain.tag.model.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTagRequest {

    private List<@NotBlank @Size(max = 10)String> tags;

    public static CreateTagRequest of(List<String> tags){
        return new CreateTagRequest(tags);
    }
}

