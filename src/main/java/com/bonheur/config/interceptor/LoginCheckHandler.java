package com.bonheur.config.interceptor;


import com.bonheur.domain.common.exception.UnAuthorizedException;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {

    private final SessionRepository<? extends Session> sessionRepository;
    private final MemberRepository memberRepository;

    Long getUserId(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorization)) {
            throw new UnAuthorizedException("인증이 실패하였습니다 - Authorization 헤더가 존재하지 않습니다");
        }
        Session session = findSessionBySessionId(authorization);
        Long userId = session.getAttribute("MEMBER_ID");
        if (!isValidUserId(userId)) {
            throw new UnAuthorizedException(String.format("인증이 실패하였습니다 - SessionId(%s)에 해당하는 유저는 회원 탈퇴되거나 유효하지 않은 유저 (%s) 입니다 ", userId, session.getId()));
        }
        return userId;
    }

    private Session findSessionBySessionId(String sessionId) {
        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            throw new UnAuthorizedException(String.format("인증이 실패하였습니다 - SessionId(%s)에 해당하는 세션은 존재하지 않습니다", sessionId));
        }
        return session;
    }

    private boolean isValidUserId(@Nullable Long userId) {
        return userId != null && memberRepository.existsById(userId);
    }

}
