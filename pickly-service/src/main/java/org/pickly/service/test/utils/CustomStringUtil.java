package org.pickly.service.test.utils;

import org.springframework.stereotype.Component;

@Component
public class CustomStringUtil {

  private static final char SEPARATOR = '/';

  public String inputSubString(String str) {
    if (str == null) {
      return null;
    }
    int separatorIndex = str.lastIndexOf(SEPARATOR);
    return (separatorIndex < 0) ? str : str.substring(0, separatorIndex);
  }

}
