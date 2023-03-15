package org.pickly.service.common.config;

import java.util.List;
import org.pickly.common.access.AccessAutoConfiguration.ApiAccessConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends ApiAccessConfig {

  private static final String[] AUTH_WHITELIST = {
      "/api/**", "/graphiql", "/graphql",
      "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
      "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
  };

  public SecurityConfig(
      List<WebContentInterceptor> interceptors,
      List<HttpMessageConverter<?>> httpMessageConverters) {
    super(interceptors, httpMessageConverters);
  }

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
        .cors().disable()
        .csrf().disable()
        .build();
  }

}
