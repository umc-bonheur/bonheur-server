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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public GetMemberProfileResponse getMemberProfile(Long memberId){
        Member member = memberRepository.findMemberById(memberId);
        return GetMemberProfileResponse.of(member.getNickname(),
                (member.getProfile() == null) ? null : member.getProfile().getUrl());
    }

    @Override
    @Transactional
    public FindActiveRecordResponse findMyActiveRecord(Long memberId) {
        FindActiveRecordResponse response = memberRepository.findCountHappyAndCountTagByMemberId(memberId);
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
    public List<FindTagRecordResponse> findMyTagRecord(Long memberId) { return memberRepository.findTagRecordByMemberId(memberId); }

    @Override
    @Transactional
    public List<FindTimeRecordResponse> findMyTimeRecord(Long memberId) {
        List<FindTimeRecordResponse> response = Arrays.asList(
                FindTimeRecordResponse.createFindTimeRecordResponse("morning", memberRepository.findTimeRecordByMemberId(memberId, 6, 12)),
                FindTimeRecordResponse.createFindTimeRecordResponse("afternoon", memberRepository.findTimeRecordByMemberId(memberId, 12, 18)),
                FindTimeRecordResponse.createFindTimeRecordResponse("evening", memberRepository.findTimeRecordByMemberId(memberId, 18, 20)),
                FindTimeRecordResponse.createFindTimeRecordResponse("night", memberRepository.findNightTimeRecordByMemberId(memberId)),
                FindTimeRecordResponse.createFindTimeRecordResponse("dawn", memberRepository.findTimeRecordByMemberId(memberId, 1, 6)));

        // 가장 많이 기록된 횟수 구하기
        Long maxCount = response.stream().map(x -> x.getCountTime()).max(Long::compare).get();

        // maxCount와 동일하다면 해당 요일을 mostRecordTime로 설정
        response.stream()
                .filter(x -> x.getCountTime() == maxCount)
                .forEach(x -> x.updateMostRecordTime());

        return response;
    }

    @Override
    @Transactional
    public List<FindDayRecordResponse> findMyDayRecord(Long memberId) {
        List<FindDayRecordResponse> response = Stream.of("sun", "mon", "tue", "wed", "thr", "fri", "sat")
                .map(day -> FindDayRecordResponse.createFindDayRecordResponse(day, 0L)).collect(Collectors.toList());

        List<FindDayRecordResponse> findDayRecordResponseList = memberRepository.findDayRecordByMemberId(memberId);

        // 가장 많이 기록된 횟수 구하기
        Long maxCount = findDayRecordResponseList.stream().map(FindDayRecordResponse::getCountDay).max(Long::compare).orElse(0L);

        // countDay 값이 존재한다면 update & maxCount와 동일하다면 해당 요일을 mostRecordDay로 설정
        for (FindDayRecordResponse findDay : findDayRecordResponseList){
            response.stream()
                    .filter(x -> x.getDayOfWeek().equals(findDay.getDayOfWeek()))
                    .forEach(x -> {
                        x.updateCountDay(findDay.getCountDay());
                        if (x.getCountDay().equals(maxCount)) {x.updateMostRecordDay();}
                    });
        }

        return response;
    }

    @Override
    @Transactional
    public List<FindMonthRecordResponse> findMyMonthRecord(Long memberId) {
        List<FindMonthRecordResponse> response = Stream.of("jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sept", "oct", "nov", "dec")
                .map(month -> FindMonthRecordResponse.createFindMonthRecordResponse(month, 0L)).collect(Collectors.toList());

        // 월별 기록 조회
        List<FindMonthRecordResponse> findMonthRecordResponseList = memberRepository.findMonthRecordByMemberId(memberId);

        // 가장 많이 기록된 횟수 구하기
        Long maxCount = findMonthRecordResponseList.stream().map(FindMonthRecordResponse::getCountMonth).max(Long::compare).orElse(0L);

        // countMonth 값이 존재한다면 update & maxCount와 동일하다면 해당 월을 mostRecordMonth로 설정
        for ( FindMonthRecordResponse findMonth : findMonthRecordResponseList) {
            response.stream()
                    .filter(x -> x.getMonth().equals(findMonth.getMonth()))
                    .forEach(x -> {
                        x.updateCountMonth(findMonth.getCountMonth());
                        if(findMonth.getCountMonth().equals(maxCount)){ x.updateMostRecordMonth(); }
                    });
        }

        return response;
    }

    @Override
    @Transactional
    public List<GetTagUsedByMemberResponse> getTagUsedByMember(Long memberId) {
        return memberRepository.getTagUsedByMember(memberId).stream().map(tag -> GetTagUsedByMemberResponse.of(tag.getId(),tag.getName())).collect(Collectors.toList());
    }
}


