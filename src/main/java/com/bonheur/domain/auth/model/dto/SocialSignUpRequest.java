package com.bonheur.domain.auth.model.dto;

import com.bonheur.config.validator.NickName;
import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialSignUpRequest {
    @NotBlank
    private String token;

    @NickName
    @NotBlank
    @Length(min = 1, max = 7)
    private String nickname;

    @NotNull
    private MemberSocialType socialType;

    public CreateMemberRequest toCreateMemberRequest(String socialId) {
        return CreateMemberRequest.of(socialId, socialType, nickname);
    }
}
