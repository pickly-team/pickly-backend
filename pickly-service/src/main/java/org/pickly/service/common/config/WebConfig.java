package org.pickly.service.common.config;

import access.AccessAutoConfiguration.ApiAccessConfig;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class WebConfig extends ApiAccessConfig {

  public WebConfig(
      List<WebContentInterceptor> interceptors,
      List<HttpMessageConverter<?>> httpMessageConverters) {
    super(interceptors, httpMessageConverters);
  }

}
