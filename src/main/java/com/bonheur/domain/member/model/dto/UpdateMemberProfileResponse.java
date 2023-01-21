package com.bonheur.domain.member.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileResponse {
    private Long memberId;

    public static UpdateMemberProfileResponse of(@NotNull Long memberId){
        return new UpdateMemberProfileResponse(memberId);
    }
}