package version;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

  @Override
  protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
    VersionResource condition = AnnotationUtils.findAnnotation(handlerType, VersionResource.class);
    log.info("Type condition: {}", condition);
    return createCondition(condition);
  }

  @Override
  protected RequestCondition<?> getCustomMethodCondition(Method method) {
    VersionResource condition = AnnotationUtils.findAnnotation(method, VersionResource.class);
    log.info("Method condition: {}", condition);
    return createCondition(condition);
  }

  private RequestCondition<?> createCondition(VersionResource versionResource) {
    if (versionResource != null) {
      return new VersionResourceRequestCondition(versionResource.media(), versionResource.from(),
          versionResource.to());
    }

    return null;
  }
}
