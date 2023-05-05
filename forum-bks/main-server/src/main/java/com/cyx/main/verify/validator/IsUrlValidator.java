package com.cyx.main.verify.validator;

import com.cyx.common.utils.ValidatorUtil;
import com.cyx.main.verify.annotation.IsUrl;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsUrlValidator implements ConstraintValidator<IsUrl, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s) || s.length() == 0) {
            return true;
        } else {
            return ValidatorUtil.isUrl(s);
        }
    }
}
