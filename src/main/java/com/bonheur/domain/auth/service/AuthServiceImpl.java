package com.bonheur.domain.auth.service;

import com.bonheur.config.provider.AuthProvider;
import com.bonheur.domain.auth.model.dto.LoginRequest;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import com.bonheur.domain.member.model.Member;
import com.bonheur.domain.member.repository.MemberRepository;
import com.bonheur.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthProviderFinder authProviderFinder;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Override
    public Long signUp(SocialSignUpRequest socialSignUpRequest, MultipartFile profileImage) throws IOException {
        AuthProvider authProvider = authProviderFinder.findAuthProvider(socialSignUpRequest.getSocialType());
        String socialId = authProvider.getSocialId(socialSignUpRequest.getToken());
        return memberService.registerMember(socialSignUpRequest.toCreateMemberRequest(socialId),profileImage);
    }

    @Override
    @Transactional(readOnly = true)
    public Long login(LoginRequest request) {
        AuthProvider authProvider = authProviderFinder.findAuthProvider(request.getSocialType());
        String socialId = authProvider.getSocialId(request.getToken());
        return memberRepository.findMemberBySocialInfo(socialId, request.getSocialType()).getId();
    }

    @Override
    public void withdrawal(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }
        // 게시물 삭제 로직 추가 필요
        memberRepository.delete(member.get());
    }
}
