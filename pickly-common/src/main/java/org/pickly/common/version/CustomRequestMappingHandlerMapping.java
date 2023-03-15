package org.pickly.common.version;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/*
 * CustomRequestMappingHandlerMapping은 RequestMappingHandlerMapping을 상속받아
 * 버전관리를 위한 커스텀 RequestMappingHandlerMapping을 생성한다.
 * @VersionResource 어노테이션을 찾아 버전 정보를 추출해 새로운 버전 관리 RequestCondition을 생성한다.
 * 상속받은 getCustomTypeCondition()과 getCustomMethodCondition() 메소드를 오버라이드하여
 * 커스텀 타입 및 메소드 조건을 찾아서 해당 조건을 만족하는 RequestCondition 객체를 반환합니다.
 */
@Slf4j
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

  /*
   * getCustomTypeCondition() 메소드는 컨트롤러 타입에 @VersionResource 어노테이션이 선언되어 있는지 확인한다.
   * 선언되어 있다면 해당 어노테이션의 정보를 추출하여 새로운 버전 관리 RequestCondition을 생성한다.
   * 선언되어 있지 않다면 null을 반환한다.
   */
  @Override
  protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
    VersionResource condition = AnnotationUtils.findAnnotation(handlerType, VersionResource.class);
    log.info("Type condition: {}", condition);
    return createCondition(condition);
  }

  /*
   * getCustomMethodCondition() 메소드는 컨트롤러 메소드에 @VersionResource 어노테이션이 선언되어 있는지 확인한다.
   */
  @Override
  protected RequestCondition<?> getCustomMethodCondition(Method method) {
    VersionResource condition = AnnotationUtils.findAnnotation(method, VersionResource.class);
    log.info("Method condition: {}", condition);
    return createCondition(condition);
  }

  /*
   * createCondition() 메소드는 버전 관리 RequestCondition을 생성한다.
   */
  private RequestCondition<?> createCondition(VersionResource versionResource) {
    if (versionResource != null) {
      return new VersionResourceRequestCondition(versionResource.media(), versionResource.from(),
          versionResource.to());
    }
    return null;
  }
}
