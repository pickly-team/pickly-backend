package org.pickly.service.common.filter;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pickly.service.common.utils.base.AuthTokenUtil;
import org.pickly.service.common.utils.base.RequestUtil;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final AuthTokenUtil authTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws IOException, ServletException {
    String bearerToken = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
    FirebaseToken decodedToken = authTokenUtil.validateToken(bearerToken);

    //TODO: decodedToken security context에 저장 필요
    filterChain.doFilter(request, response);
  }
}
