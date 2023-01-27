package com.bonheur.domain.member.service;

import com.bonheur.domain.member.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long registerMember(CreateMemberRequest request);

    UpdateMemberProfileResponse updateMemberProfile(Long memberId, UpdateMemberProfileRequest request, MultipartFile image) throws IOException;

    FindActiveRecordResponse findMyActiveRecord(Long memberId);

    List<FindTagRecordResponse> findMyTagRecord(Long memberId);

    FindTimeRecordResponse findMyTimeRecord(Long memberId);

    List<FindDayRecordResponse> findMyDayRecord(Long memberId);
    List<FindMonthRecordResponse> findMyMonthRecord(Long memberId);

    List<GetTagUsedByMemberResponse> getTagUsedByMember(Long memberId);
}
