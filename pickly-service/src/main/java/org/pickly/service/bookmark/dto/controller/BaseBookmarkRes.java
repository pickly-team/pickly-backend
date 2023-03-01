package org.pickly.service.bookmark.dto.controller;

import java.util.List;

public interface BaseBookmarkRes<T> {

  List<T> getContents();
  boolean hasNext();

}
