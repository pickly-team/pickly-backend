package org.pickly.service.bookmark.entity;

import lombok.Getter;

@Getter
public enum Visibility {

  SCOPE_PRIVATE("나만 보기"),
  SCOPE_FRIEND("친구 공개"),
  SCOPE_PUBLIC("전체 공개");

  private String description;

  Visibility(String description) {
    this.description = description;
  }

}