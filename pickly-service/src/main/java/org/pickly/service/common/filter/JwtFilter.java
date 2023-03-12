package org.pickly.service.common.filter;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.pickly.service.common.utils.base.RequestUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final FirebaseAuth firebaseAuth;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    FirebaseToken decodedToken;

    try{
      String bearerToken = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
      decodedToken = firebaseAuth.verifyIdToken(bearerToken);
    }catch (FirebaseAuthException | IllegalArgumentException e){
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
      return;
    }

    try{
      UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getUid());
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }catch (NoSuchElementException e){
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      response.setContentType("application/json");
      response.getWriter().write("{\"code\":\"USER_NOT_FOUND\"}");
      return;
    }

    filterChain.doFilter(request, response);
  }
}
