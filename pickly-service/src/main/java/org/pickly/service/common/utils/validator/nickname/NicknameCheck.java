package org.pickly.service.common.utils.validator.nickname;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NicknameValidator.class)
public @interface NicknameCheck {

  String message() default "닉네임은 7글자 이하의 영문 대소문자, 숫자, 한글만 허용됩니다.";

  Class[] groups() default {};

  Class[] payload() default {};

}
