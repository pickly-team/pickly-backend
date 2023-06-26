package org.pickly.service.common.config;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.filter.CorsWebFilter;
import org.pickly.service.common.filter.JwtFilter;
import org.pickly.service.common.utils.base.AuthTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsWebFilter corsFilter;

  private final UserDetailsService userDetailsService;
  private final AuthTokenUtil authTokenUtil;

  private static final String[] AUTH_WHITELIST = {
      "/api/**", "/graphiql", "/graphql",
      "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
      "/v3/api-docs/**", "/api-docs/**",
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .authorizeHttpRequests(
            authorize -> authorize
                .shouldFilterAllDispatcherTypes(false)
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtFilter(
                    userDetailsService,
                    authTokenUtil
                ), UsernamePasswordAuthenticationFilter.class)
        )
        .httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .addFilter(corsFilter.corsFilter())
        .build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
  }
}
