package com.bonheur.domain.auth.model.dto;

import com.bonheur.domain.member.model.MemberSocialType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotBlank
    private String token;

    @NotNull
    private MemberSocialType socialType;

}
