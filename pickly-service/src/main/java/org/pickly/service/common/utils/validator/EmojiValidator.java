package org.pickly.service.common.utils.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmojiValidator implements ConstraintValidator<EmojiCheck, String> {

  @Override
  public boolean isValid(String emoji, ConstraintValidatorContext context) {

    if (checkIsEmpty(emoji)) {
      return false;
    }

    if (checkCodePointCount(emoji)) {
      return false;
    }

    int codePoint = emoji.codePointAt(0);
    return isEmoji(codePoint);
  }

  private static boolean checkIsEmpty(String value) {
    return value == null || value.length() == 0;
  }

  private static boolean checkCodePointCount(String value) {
    int codePointCnt = value.codePointCount(0, value.length());
    return codePointCnt > 1;
  }

  private static boolean isEmoji(int codePoint) {
    return (codePoint == 0x0)
        || (codePoint == 0x9)
        || (codePoint == 0xA)
        || (codePoint == 0xD)
        || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
        || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
        || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
  }

}
