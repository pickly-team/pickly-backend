package org.pickly.service.common.utils.validator.url;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class UrlValidator implements ConstraintValidator<UrlCheck, String> {
  @Override
  public boolean isValid(String inputUrl, ConstraintValidatorContext context) {
    try {
      URL url = new URL(inputUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
    } catch (IOException e) {
      log.error("msg = {}", e.getMessage());
      throw new InvalidValueException("유효하지 않은 URL 입니다.");
    }
  }
}
