package org.pickly.scheduler;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EnableBatchProcessing // 스프링 프로젝트에서 배치 사용을 할 수 있도록 하는 어노테이션
public class SchedulerApplication {
    private static final String DEV_PROPERTIES = "optional:pickly-scheduler/src/main/resources/application-dev.yml";
    private static final String LOCAL_PROPERTIES = "optional:pickly-scheduler/src/main/resources/application-local.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SchedulerApplication.class)
                .properties("spring.config.additional-location=" + LOCAL_PROPERTIES)
                .run(args);
    }
}
