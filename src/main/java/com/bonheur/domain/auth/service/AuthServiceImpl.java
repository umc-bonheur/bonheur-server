package com.bonheur.domain.auth.service;

import com.bonheur.config.provider.AuthProvider;
import com.bonheur.domain.auth.model.dto.SocialSignUpRequest;
import com.bonheur.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthProviderFinder authProviderFinder;
    private final MemberService memberService;
    @Override
    public Long signUp(SocialSignUpRequest request) {
        AuthProvider authProvider = authProviderFinder.findAuthProvider(request.getSocialType());
        String socialId = authProvider.getSocialId(request.getToken());
        return memberService.registerMember(request.toCreateMemberRequest(socialId));
    }
}
