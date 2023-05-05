package com.cyx.main.verify.validator;

import com.cyx.common.utils.ValidatorUtil;
import com.cyx.main.verify.annotation.IsMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//电话号码可以为空，但是不可以不合规范
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s.length()==0|| ValidatorUtil.isMobile(s)){
            return true;
        }else{
            return ValidatorUtil.isMobile(s);
        }
    }
}
