package org.pickly.service.common.utils.validator.nickname;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.pickly.service.common.error.CommonErrorCode;
import org.pickly.service.common.error.exception.BusinessException;

import java.util.regex.Pattern;

public class NicknameValidator implements ConstraintValidator<NicknameCheck, String> {

  private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");


  @Override
  public boolean isValid(String inputNickname, ConstraintValidatorContext constraintValidatorContext) {
    if (!isValidNickname(inputNickname)) {
      throw new BusinessException(CommonErrorCode.INVALID_VALUE_ERROR);
    }
    return true;
  }

  private boolean isValidNickname(String nickname) {
    return NICKNAME_PATTERN.matcher(nickname).matches();
  }

}
