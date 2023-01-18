package com.bonheur.domain.auth.model.dto;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialSignUpRequest {
    @NotBlank
    private String token;

    // 닉네임 Validation 추가 필요
    private String nickname;

    @NotNull
    private MemberSocialType socialType;

    public CreateMemberRequest toCreateMemberRequest(String socialId) {
        return CreateMemberRequest.of(socialId, socialType, nickname);
    }
}
