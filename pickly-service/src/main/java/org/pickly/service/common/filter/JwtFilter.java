package org.pickly.service.common.filter;


//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtFilter extends OncePerRequestFilter {
//
//  private final UserDetailsService userDetailsService;
//  private final AuthTokenUtil authTokenUtil;
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//      FilterChain filterChain)
//      throws IOException, ServletException {
//    String bearerToken = RequestUtil.getAuthorizationToken(request.getHeader("Authorization"));
//    FirebaseToken decodedToken = authTokenUtil.validateToken(bearerToken);
//
//    //TODO: decodedToken security context에 저장 필요
//    filterChain.doFilter(request, response);
//  }
//}
