package com.bonheur.domain.member.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemberProfileResponse {
    private Long memberId;

    public static UpdateMemberProfileResponse of(Long memberId){
        return new UpdateMemberProfileResponse(memberId);
    }
}