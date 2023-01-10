package com.bonheur.domain.auth.service;

import com.bonheur.config.provider.AuthProvider;
import com.bonheur.domain.member.model.MemberSocialType;

public interface AuthProviderFinder {
    AuthProvider findAuthProvider(MemberSocialType socialType);
}