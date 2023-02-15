package com.bonheur.domain.member.service;

import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.member.model.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long registerMember(CreateMemberRequest request,MultipartFile profileImage) throws IOException;

    FileUploadResponse uploadProfileImage(MultipartFile multipartFile) throws IOException;

    UpdateMemberProfileResponse updateMemberProfile(Long memberId, UpdateMemberProfileRequest request, MultipartFile image) throws IOException;
    GetMemberProfileResponse getMemberProfile(Long memberId);

    FindActiveRecordResponse findMyActiveRecord(Long memberId);

    List<FindTagRecordResponse> findMyTagRecord(Long memberId);

    List<FindTimeRecordResponse> findMyTimeRecord(Long memberId);

    List<FindDayRecordResponse> findMyDayRecord(Long memberId);
    List<FindMonthRecordResponse> findMyMonthRecord(Long memberId);
}
