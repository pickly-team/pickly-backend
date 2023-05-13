package org.pickly.service.common.utils.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmojiValidator.class)
public @interface EmojiCheck {

  String message() default "이모지가 아닙니다.";

  Class[] groups() default {};

  Class[] payload() default {};

}
