package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.CreateMemberProfileRequest;
import com.bonheur.domain.member.model.dto.CreateMemberProfileResponse;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);
    CreateMemberProfileResponse createMemberProfile(Long memberId, CreateMemberProfileRequest request, MultipartFile image) throws IOException;
}
