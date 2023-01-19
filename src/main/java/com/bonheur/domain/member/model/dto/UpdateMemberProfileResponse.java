package com.bonheur.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateMemberProfileResponse {
    private Long memberId;

    @Builder
    private UpdateMemberProfileResponse(Long memberId){
        this.memberId = memberId;
    }

    public static UpdateMemberProfileResponse of(Long memberId){
        return UpdateMemberProfileResponse.builder()
                .memberId(memberId)
                .build();
    }
}
