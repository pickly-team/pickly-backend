package org.pickly.service.common.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlValidator implements ConstraintValidator<UrlCheck, String> {
  @Override
  public boolean isValid(String inputUrl, ConstraintValidatorContext context) {
    try {
      new URL(inputUrl);
      return true;
    } catch (MalformedURLException e) {
      return false;
    }
  }
}
