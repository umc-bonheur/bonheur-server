package com.bonheur.domain.member.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMemberProfileResponse {
    private Long memberId;

    @Builder
    private CreateMemberProfileResponse(Long memberId){
        this.memberId = memberId;
    }

    public static CreateMemberProfileResponse of(Long memberId){
        return CreateMemberProfileResponse.builder()
                .memberId(memberId)
                .build();
    }
}
