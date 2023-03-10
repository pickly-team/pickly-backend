package org.pickly.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ServiceApplication {

  private static final String DEV_PROPERTIES = "optional:pickly-service/src/main/resources/application-dev.yml";
  private static final String LOCAL_PROPERTIES = "optional:pickly-service/src/main/resources/application-local.yml";

  private static final String TEST_PROPERTIES = "optional:pickly-service/src/main/resources/application-test.yml";

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServiceApplication.class)
        .properties("spring.config.additional-location=" + TEST_PROPERTIES)
        .run(args);
  }
}
