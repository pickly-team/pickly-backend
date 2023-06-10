package org.pickly.service.common.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pickly.common.error.exception.InvalidValueException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlValidator implements ConstraintValidator<UrlCheck, String> {
  @Override
  public boolean isValid(String inputUrl, ConstraintValidatorContext context) {
    try {
      URL url = new URL(inputUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    } catch (IOException e) {
      throw new InvalidValueException("유효하지 않은 URL 입니다.");
    }
  }
}
