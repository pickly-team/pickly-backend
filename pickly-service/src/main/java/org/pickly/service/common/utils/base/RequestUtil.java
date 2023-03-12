package org.pickly.service.common.utils.base;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {
  public static String getAuthorizationToken(String header){
    if(!header.startsWith("Bearer ")){
      throw new IllegalArgumentException("Invalid authorization header");
    }

    String[] parts= header.split(" ");

    if(parts.length!=2){
      throw new IllegalArgumentException("Invalid authorization header");
    }

    return parts[1];
  }
}
