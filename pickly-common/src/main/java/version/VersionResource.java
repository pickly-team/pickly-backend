package version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionResource {

  // 인터페이스 내, static final은 생략 가능 ( static final의 형대로 들어가게 되어짐 )
  String VERSION_RESOURCE = "application/vnd.pickly.resource";

  String media() default VERSION_RESOURCE;

  String from() default "";

  String to() default Version.MAX_VERSION;
}
