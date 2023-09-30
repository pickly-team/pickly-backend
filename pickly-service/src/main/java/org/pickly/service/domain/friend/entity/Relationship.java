package org.pickly.service.domain.friend.entity;

import lombok.Getter;
import org.pickly.service.domain.bookmark.entity.Visibility;

import java.util.List;

@Getter
public enum Relationship {

  ME,
  FRIEND,
  OTHERS;

  public List<Visibility> getVisibility() {
    return switch (this) {
      case FRIEND -> List.of(Visibility.SCOPE_PUBLIC, Visibility.SCOPE_FRIEND);
      case ME -> List.of(Visibility.SCOPE_PUBLIC, Visibility.SCOPE_FRIEND, Visibility.SCOPE_PRIVATE);
      default -> List.of(Visibility.SCOPE_PUBLIC);
    };
  }

}
