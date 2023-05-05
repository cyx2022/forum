package com.cyx.main.verify.validator;

import com.cyx.main.verify.annotation.IsImage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * 手机号码校验规则
 *
 * @author: LC
 * @date 2022/3/2 3:08 下午
 * @ClassName: IsImageValidator
 */
public class IsImageValidator implements ConstraintValidator<IsImage, String> {
    private Class<?> type;
    private String name;
    private String path="http://127.0.0.1:9000/img/default.jpg";
    @Override
    public void initialize(IsImage constraintAnnotation) {
        this.type = constraintAnnotation.type();
        this.name=constraintAnnotation.name();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            try {
                Field field = type.getDeclaredField(this.name);
                field.setAccessible(true);
                field.set(value, path);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
