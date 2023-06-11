package org.pickly.service.common.utils.validator.visibility;

import jakarta.validation.Constraint;
import org.pickly.service.common.utils.validator.url.UrlValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidator.class)
public @interface VisibilityCheck {

  String message() default "유효하지 않은 공개 범위 입니다.";

  Class[] groups() default {};

  Class[] payload() default {};

}
