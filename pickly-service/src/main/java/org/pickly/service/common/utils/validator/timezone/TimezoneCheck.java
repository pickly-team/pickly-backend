package org.pickly.service.common.utils.validator.timezone;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimezoneValidator.class)
public @interface TimezoneCheck {

  String message() default "유효한 timezone이 아닙니다.";

  Class[] groups() default {};

  Class[] payload() default {};

}
