package org.pickly.service.common.utils.validator.timezone;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.ZoneId;

public class TimezoneValidator implements ConstraintValidator<TimezoneCheck, String> {
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return ZoneId.getAvailableZoneIds().contains(value);
  }
}
