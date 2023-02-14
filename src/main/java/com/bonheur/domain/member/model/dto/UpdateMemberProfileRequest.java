package com.bonheur.domain.member.model.dto;

import com.bonheur.config.validator.NickName;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileRequest {

    @NickName
    @Length(min = 1, max = 7)
    @NotBlank
    private String nickname;

    public static UpdateMemberProfileRequest of(String nickname){
        return new UpdateMemberProfileRequest(nickname);
    }
}
