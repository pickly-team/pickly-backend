package org.pickly.service.common.utils.validator.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumCheck, String> {

  private EnumCheck annotation;

  @Override
  public void initialize(EnumCheck constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean result = false;
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();
    if (enumValues != null) {
      for (Object enumValue : enumValues) {
        if (value.equals(enumValue.toString())
            || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
          result = true;
          break;
        }
      }
    }
    return result;
  }

}
