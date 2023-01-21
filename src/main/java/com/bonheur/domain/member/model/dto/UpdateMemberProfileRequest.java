package com.bonheur.domain.member.model.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileRequest {

    @NotNull
    @Length(max = 7)
    private String nickname;

    public static UpdateMemberProfileRequest of(@NotNull String nickname){
        return new UpdateMemberProfileRequest(nickname);
    }
}
