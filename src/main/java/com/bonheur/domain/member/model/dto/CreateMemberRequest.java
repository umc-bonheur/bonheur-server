package com.bonheur.domain.member.model.dto;

import com.bonheur.config.validator.NickName;
import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.MemberSocialType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberRequest {
    private String socialId;

    @NotNull
    private MemberSocialType socialType;

    // 닉네임 조건 체크 어노테이션 이후 추가
    @Length(max = 7)
    @NickName
    private String nickname;

    @Builder(access = AccessLevel.PRIVATE)
    private CreateMemberRequest(String socialId, MemberSocialType socialType, String nickname) {
        this.socialId = socialId;
        this.socialType = socialType;
        this.nickname = nickname;
    }

    public static CreateMemberRequest of(String socialId, MemberSocialType socialType, String nickname) {
        return CreateMemberRequest.builder()
                .socialId(socialId)
                .socialType(socialType)
                .nickname(nickname)
                .build();
    }

    public Member toEntity(FileUploadResponse fileUploadResponse) {
        return Member.newMember(socialId, socialType, nickname, fileUploadResponse.getFileUrl(), fileUploadResponse.getFilePath());
    }
}
