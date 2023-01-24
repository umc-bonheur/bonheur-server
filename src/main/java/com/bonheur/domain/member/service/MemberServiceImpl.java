package com.bonheur.domain.member.service;

import com.bonheur.domain.board.repository.BoardRepository;
import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.dto.*;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileUploadUtil fileUploadUtil;

    @Override
    @Transactional
    public Long registerMember(CreateMemberRequest request) {
        if (memberRepository.existMemberBySocialInfo(request.getSocialId(), request.getSocialType())) {
            throw new RuntimeException("이미 가입한 유저 입니다.");
        }
        Member member = memberRepository.save(request.toEntity());
        return member.getId();
    }

    @Override
    @Transactional
    public UpdateMemberProfileResponse updateMemberProfile(Long memberId, UpdateMemberProfileRequest request, MultipartFile image) throws IOException {
        Member member = memberRepository.findMemberById(memberId);
        //닉네임 수정
        member.updateNickname(request.getNickname());

        //이미지 수정
        if(member.getProfile() != null){    //기존의 프로필 이미지가 있는 경우
            fileUploadUtil.deleteFile(member.getProfile().getPath());   //프로필 이미지 s3에서 삭제
            member.updateProfile(null, null); //member 테이블에서 이미지 삭제
        }
        if(!image.isEmpty()){   //기존의 프로필 이미지를 새로운 이미지로 변경하는 경우
            FileUploadResponse fileUploadResponse = fileUploadUtil.uploadFile("image", image);  //프로필 이미지 s3에 업로드
            member.updateProfile(fileUploadResponse.getFileUrl(), fileUploadResponse.getFilePath());  //프로필 이미지 변경
        }

        return UpdateMemberProfileResponse.of(memberId);
    }

    @Override
    @Transactional
    public FindAllActiveResponse findAllActive(Long memberId) {
        FindAllActiveResponse response = memberRepository.findCountHappyAndCountTag(memberId);
        Member findMember = memberRepository.findById(memberId).orElse(null);

        return response.updateActiveDayAndRecordDay(
                // 앱을 사용한 날
                ChronoUnit.DAYS.between((findMember.getCreatedAt()), LocalDateTime.now().plusDays(1)),

                // 기록 작성한 날
                boardRepository.findByMember(findMember)
                        .stream().collect(Collectors.groupingBy(board -> board.getCreatedAt().toLocalDate())).values().stream().count()
        );
    }

    @Override
    @Transactional
    public List<FindByTagResponse> findByTag(Long memberId) {
        return memberRepository.findByTag(memberId);
    }

    @Override
    @Transactional
    public FindByTimeResponse findByTime(Long memberId) {
        Long morning = memberRepository.findByTime(memberId, 6, 12);
        Long afternoon = memberRepository.findByTime(memberId, 12, 18);
        Long evening = memberRepository.findByTime(memberId, 18, 20);
        Long night = memberRepository.findNightTime(memberId);
        Long dawn = memberRepository.findByTime(memberId, 1, 6);

        return FindByTimeResponse.of(morning,afternoon,evening,night,dawn);
    }

    @Override
    @Transactional
    public List<FindByDayResponse> findByDay(Long memberId) { return memberRepository.findByDay(memberId); }

    @Override
    @Transactional
    public List<FindByMonthResponse> findByMonth(Long memberId) { return memberRepository.findByMonth(memberId); }
}


