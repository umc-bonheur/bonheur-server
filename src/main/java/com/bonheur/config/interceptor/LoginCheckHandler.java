package com.bonheur.config.interceptor;


import com.bonheur.domain.common.exception.UnAuthorizedException;
import com.bonheur.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.bonheur.config.session.SessionConstant.MEMBER_ID;

@RequiredArgsConstructor
@Component
@Slf4j
public class LoginCheckHandler {
    private final MemberRepository memberRepository;

    Long getMemberId(HttpServletRequest request) {
        String sessionId = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(sessionId)) {
            throw new UnAuthorizedException("인증이 실패하였습니다 - Authorization 헤더가 존재하지 않습니다");
        }
        HttpSession session = request.getSession();
        Object memberId = session.getAttribute(MEMBER_ID);
        if (!isValidMemberId((Long) memberId)) {
            throw new UnAuthorizedException(String.format("인증이 실패하였습니다 - SessionId(%s)에 해당하는 유저는 회원 탈퇴되거나 유효하지 않은 유저 (%s) 입니다 ", memberId, session.getId()));
        }
        return (Long) memberId;
    }

    private boolean isValidMemberId(@Nullable Long memberId) {
        return memberId != null && memberRepository.existsById(memberId);
    }

}
