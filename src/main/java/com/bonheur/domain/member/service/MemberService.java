package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);

    UpdateMemberProfileResponse updateMemberProfile(Long memberId, UpdateMemberProfileRequest request, MultipartFile image) throws IOException;

    FindAllActiveResponse findAllActive(Long memberId);

    List<FindByTagResponse> findByTag(Long memberId);

    FindByTimeResponse findByTime(Long memberId);

    List<FindByDayResponse> findByDay(Long memberId);
    FindByMonthResponse findByMonth(Long memberId);
}
