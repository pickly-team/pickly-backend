package org.pickly.service.common.utils.validator.visibility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pickly.service.bookmark.entity.Visibility;

public class VisibilityValidator implements ConstraintValidator<VisibilityCheck, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    try {
      Visibility.valueOf(value);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
