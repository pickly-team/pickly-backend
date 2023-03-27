package access;

import jakarta.servlet.Servlet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import version.CustomRequestMappingHandlerMapping;

@Configuration
@ConditionalOnProperty(
    prefix = "pickly.enabled",
    name = "access",
    havingValue = "true"
)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean({WebMvcAutoConfiguration.class})
@AutoConfigureBefore({WebMvcAutoConfiguration.class})
public class AccessAutoConfiguration {


  /*
   * AccessAutoConfiguration이 선언된 interceptor를 injection 받기 위해 이너클래스로 선언
   * TODO : 내부 구현로직 한번 더 파보면서 공부하면 큰 도움될듯
   */
  @Configuration
  @RequiredArgsConstructor
  public static class ApiAccessConfig extends WebMvcConfigurationSupport {

    private final List<WebContentInterceptor> interceptors;

    private final List<HttpMessageConverter<?>> httpMessageConverters;

    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(
        ContentNegotiationManager contentNegotiationManager,
        FormattingConversionService formattingConversionService,
        ResourceUrlProvider resourceUrlProvider) {

      CustomRequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
      handlerMapping.setOrder(0);
      handlerMapping.setInterceptors(
          getInterceptors(formattingConversionService, resourceUrlProvider));
      handlerMapping.setContentNegotiationManager(contentNegotiationManager);

      if (interceptors != null && interceptors.size() != 0) {
        handlerMapping.setInterceptors(interceptors.toArray());
      }

      return handlerMapping;
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.addAll(this.httpMessageConverters);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
      configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

  }

}
