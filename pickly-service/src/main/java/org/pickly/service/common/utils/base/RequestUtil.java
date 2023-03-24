package org.pickly.service.common.utils.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtil {

  private static final String BEARER_PREFIX = "Bearer";

  public static String getAuthorizationToken(String header) {
    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      throw new IllegalArgumentException("Invalid authorization header");
    }
    return RequestUtil.getBearerTokenFromHeader(header);
  }

  private static String getBearerTokenFromHeader(String authorization) {
    String[] parts = authorization.split(" ");

    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid authorization header");
    }

    String authorizationToken = parts[1];
    return authorizationToken;
  }
}
