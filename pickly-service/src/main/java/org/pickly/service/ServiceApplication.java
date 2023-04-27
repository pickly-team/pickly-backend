package org.pickly.service;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulShutdownHook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ServiceApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(ServiceApplication.class);
  }

  public static void main(String[] args) {
    // DESCRIBE: 웹 어플리케이션 구동 시 프로세스 ID를 classpath에 기록합니다.
    try {
      SpringApplication application = new SpringApplicationBuilder()
          .sources(ServiceApplication.class)
          .listeners(new ApplicationPidFileWriter("./application.pid"))
          .build();
      application.setRegisterShutdownHook(false);
      ConfigurableApplicationContext applicationContext = application.run(args);
      Runtime.getRuntime()
          .addShutdownHook(new Thread(new GracefulShutdownHook(applicationContext)));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
