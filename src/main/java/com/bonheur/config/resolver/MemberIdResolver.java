package com.bonheur.config.resolver;

import com.bonheur.config.interceptor.Auth;
import com.bonheur.domain.common.exception.InternalServerException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
public class MemberIdResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class) && Long.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
        if (parameter.getMethodAnnotation(Auth.class) == null) {
            throw new InternalServerException(String.format("예상치 못한 에러가 발생하였습니다. 인증이 필요한 컨트롤러(%s-%s)로, @Auth 어노테이션을 붙여주세요.", parameter.getDeclaringClass().getSimpleName(), Objects.requireNonNull(parameter.getMethod()).getName()));
        }
        Object object = webRequest.getAttribute("MEMBER_ID", 0);
        if (object == null) {
            throw new InternalServerException(String.format("예상치 못한 에러가 발생하였습니다. 컨트롤러(%s-%s)에서 MEMBER_ID 가져오지 못했습니다.", parameter.getDeclaringClass().getSimpleName(), Objects.requireNonNull(parameter.getMethod()).getName()));
        }
        return object;
    }
}
