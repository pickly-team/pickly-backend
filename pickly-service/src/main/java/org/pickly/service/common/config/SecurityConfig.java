package org.pickly.service.common.config;

import lombok.RequiredArgsConstructor;
import org.pickly.service.common.filter.CorsWebFilter;
import org.pickly.service.common.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsWebFilter corsFilter;

  private final JwtFilter jwtFilter;

  private static final String[] AUTH_WHITELIST = {
      "/api/**", "/graphiql", "/graphql",
      "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
      "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
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
        )
        .httpBasic().disable()
        .formLogin().disable()
        .csrf().disable()
        .addFilter(corsFilter.corsFilter())
//        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

}
