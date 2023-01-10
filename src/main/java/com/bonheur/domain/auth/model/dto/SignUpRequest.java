package com.bonheur.domain.auth.model.dto;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {
    private String token;

    private String nickname;

    private MemberSocialType socialType;

    public CreateMemberRequest toCreateMemberRequest(String socialId) {
        return CreateMemberRequest.builder()
                .socialId(socialId)
                .socialType(socialType)
                .nickname(nickname)
                .build();
    }
}
