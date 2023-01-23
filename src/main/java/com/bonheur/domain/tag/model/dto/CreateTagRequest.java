package com.bonheur.domain.tag.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTagRequest {

    @Length(max = 10)
    private List<String> tags;


    public static CreateTagRequest of(List<String> tags){
        return new CreateTagRequest(tags);
    }
}

