package com.bonheur.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 닉네임 규칙
 * 한글만 허용한다.
 * 2자 이상, 7자 이하
 */
public class NickNameValidator implements ConstraintValidator<NickName, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank())
            return false;

        Pattern pattern = Pattern.compile("^[가-힣]*$");
        return pattern.matcher(value).find();
    }
}
