package org.pickly.service.common.utils.validator.url;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.error.CommonErrorCode;
import org.pickly.service.common.error.exception.BusinessException;

import java.util.regex.Pattern;

@Slf4j
public class UrlValidator implements ConstraintValidator<UrlCheck, String> {

  final static String URL_REGEX = "https?://[^\\s]+";

  @Override
  public boolean isValid(String inputUrl, ConstraintValidatorContext context) {
//    try {
//      URL url = new URL(inputUrl);
//      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//      connection.setRequestMethod("GET");
//      return connection.getResponseCode() == HttpURLConnection.HTTP_OK;
//    } catch (IOException e) {
//      log.error("msg = {}", e.getMessage());
//      throw new BusinessException(CommonErrorCode.INVALID_VALUE_ERROR);
//    }
    if (!isURL(inputUrl)) {
      throw new BusinessException(CommonErrorCode.INVALID_VALUE_ERROR);
    }
    return true;
  }

  public boolean isURL(String url) {
    return Pattern.matches(URL_REGEX, url);
  }

}
