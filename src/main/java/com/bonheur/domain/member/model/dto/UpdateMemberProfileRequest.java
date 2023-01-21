package com.bonheur.domain.member.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileRequest {

    @NotBlank
    @Length(max = 7)
    private String nickname;

    public static UpdateMemberProfileRequest of(String nickname){
        return new UpdateMemberProfileRequest(nickname);
    }
}
