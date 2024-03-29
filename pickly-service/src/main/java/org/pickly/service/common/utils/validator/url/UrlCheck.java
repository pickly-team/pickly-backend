package org.pickly.service.common.utils.validator.url;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidator.class)
public @interface UrlCheck {

  String message() default "유효하지 않은 url 입니다.";

  Class[] groups() default {};

  Class[] payload() default {};

}
