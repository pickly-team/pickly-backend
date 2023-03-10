package org.pickly.service.common.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory queryFactory() {
    return new JPAQueryFactory(entityManager);
  }

}

