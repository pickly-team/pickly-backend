package version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionResource {

  String media() default "application/vnd.pickly.resource";

  String from() default "";

  String to() default Version.MAX_VERSION;
}
