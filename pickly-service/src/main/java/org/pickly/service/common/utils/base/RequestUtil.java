package org.pickly.service.common.utils.base;

public class RequestUtil {

  private static final String BEARER_PREFIX = "Bearer";

  private RequestUtil() {
  }

  public static String getAuthorizationToken(String header) {
    if (header == null || !header.startsWith(BEARER_PREFIX)) {
      throw new IllegalArgumentException("Invalid authorization header");
    }

    String[] parts = header.split(" ");

    if (parts.length != 2) {
      throw new IllegalArgumentException("Invalid authorization header");
    }

    return parts[1];
  }
}
