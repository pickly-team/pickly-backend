package org.pickly.service.common.utils.cache;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExpireConcurrentMapCache extends ConcurrentMapCache {

  private final Map<Object, LocalDateTime> expires = new ConcurrentHashMap<>();
  private final Long expireAfter;

  public ExpireConcurrentMapCache(final String name, final Long expireAfter) {
    super(name);
    this.expireAfter = expireAfter;
  }

  @Override
  protected Object lookup(final Object key) {
    LocalDateTime expiredDate = expires.get(key);
    if (Objects.isNull(expiredDate) || LocalDateTime.now().isBefore(expiredDate)) {
      return super.lookup(key);
    }
    expires.remove(key);
    super.evict(key);
    return null;
  }

  @Override
  public void put(final Object key, final Object value) {
    LocalDateTime expiredAt =
        (expireAfter == null) ? null : LocalDateTime.now().plusSeconds(expireAfter);
    expires.put(key, expiredAt);
    super.put(key, value);
  }

  public void evictAllExpired() {
    ConcurrentMap<Object, Object> nativeCache = getNativeCache();
    nativeCache.keySet()
        .stream()
        .filter(key -> !isCacheValid(expires.get(key)))
        .forEach(super::evict);
  }

  private boolean isCacheValid(LocalDateTime expiredAt) {
    return LocalDateTime.now().isBefore(expiredAt);
  }

}
