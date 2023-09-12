package org.pickly.service.common.config;

import org.pickly.service.common.utils.cache.ExpireConcurrentMapCache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

@EnableCaching
@Configuration
public class CacheConfig {

  public static final String AUTHENTICATE = "authenticate";

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(Set.of(
        new ExpireConcurrentMapCache(AUTHENTICATE, 300L)
    ));
    return cacheManager;
  }

  @Scheduled(cron = "0 0 0 * * *")
  private void evict() {
    ExpireConcurrentMapCache cache = (ExpireConcurrentMapCache) cacheManager().getCache(AUTHENTICATE);
    if (cache != null) {
      cache.evictAllExpired();
    }
  }

}
