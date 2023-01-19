package com.bonheur.domain.member.service;

import com.bonheur.domain.file.dto.FileUploadResponse;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.model.dto.UpdateMemberProfileRequest;
import com.bonheur.domain.member.model.dto.UpdateMemberProfileResponse;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
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
        if(member.getProfile().getUrl() != null){    //기존의 프로필 이미지가 있는 경우
            fileUploadUtil.deleteFile(member.getProfile().getPath());   //프로필 이미지 s3에서 삭제
            member.getProfile().updateProfile(null, null); //member 테이블에서 이미지 삭제
        }
        if(!image.isEmpty()){   //기존의 프로필 이미지를 새로운 이미지로 변경하는 경우
            FileUploadResponse fileUploadResponse = fileUploadUtil.uploadFile("image", image);  //프로필 이미지 s3에 업로드
            member.getProfile().updateProfile(fileUploadResponse.getFileUrl(), fileUploadResponse.getFilePath());  //프로필 이미지 변경
        }

        return UpdateMemberProfileResponse.of(memberId);
    }
}
