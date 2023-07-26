package org.pickly.service.common.utils.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.pickly.service.common.error.CommonErrorCode;
import org.pickly.service.common.error.exception.BusinessException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtil {

  private static final String BEARER_PREFIX = "Bearer";

  public static String getAuthorizationToken(String header) {
    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      throw new BusinessException(CommonErrorCode.INVALID_AUTHORIZATION_HEADER);
    }
    return RequestUtil.getBearerTokenFromHeader(header);
  }

  private static String getBearerTokenFromHeader(String authorization) {
    String[] parts = authorization.split(" ");

    if (parts.length != 2) {
      throw new BusinessException(CommonErrorCode.INVALID_AUTHORIZATION_HEADER);
    }

    String authorizationToken = parts[1];
    return authorizationToken;
  }
}
