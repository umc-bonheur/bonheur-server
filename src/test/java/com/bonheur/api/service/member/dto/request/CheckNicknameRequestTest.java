package com.bonheur.api.service.member.dto.request;

import com.bonheur.domain.member.model.MemberSocialType;
import com.bonheur.domain.member.model.dto.CreateMemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class CheckNicknameRequestTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ValueSource(strings = {"임세환", "보네르", "ㅂㅗㄴㅔㄹㅡ", "보 네 르"})
    @ParameterizedTest
    void 사용가능한_닉네임이면_검사를_통과한다(String name) {

        CreateMemberRequest request = CreateMemberRequest.of("123", MemberSocialType.KAKAO, name);

        Set<ConstraintViolation<CreateMemberRequest>> validate = validator.validate(request);

        Assertions.assertThat(validate).isEmpty();
    }

    @ValueSource(strings = {"hwan", "보네르22", "보네르!", "보+네-르","  "})
    @ParameterizedTest
    void 사용가능하지_않은_닉네임이면_검사를_실패한다(String name) {

        CreateMemberRequest request = CreateMemberRequest.of("123", MemberSocialType.KAKAO, name);

        Set<ConstraintViolation<CreateMemberRequest>> validate = validator.validate(request);

        Assertions.assertThat(validate).hasSize(1);
    }

    @NullAndEmptySource
    @ParameterizedTest
    void 닉네임이_NULL_이거나_빈문자열이면_유효성_검사에서_실패한다(String name) {

        CreateMemberRequest request = CreateMemberRequest.of("123", MemberSocialType.KAKAO, name);

        Set<ConstraintViolation<CreateMemberRequest>> validate = validator.validate(request);

        Assertions.assertThat(validate).hasSize(1);
    }
}
