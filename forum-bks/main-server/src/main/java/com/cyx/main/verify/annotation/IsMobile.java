package com.cyx.main.verify.annotation;


import com.cyx.main.verify.validator.IsImageValidator;
import com.cyx.main.verify.validator.IsMobileValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {
                IsMobileValidator.class
        }
)
public @interface IsMobile {
}
