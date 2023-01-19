package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.UpdateMemberProfileRequest;
import com.bonheur.domain.member.model.dto.UpdateMemberProfileResponse;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);
    UpdateMemberProfileResponse updateMemberProfile(Long memberId, UpdateMemberProfileRequest request, MultipartFile image) throws IOException;
}
