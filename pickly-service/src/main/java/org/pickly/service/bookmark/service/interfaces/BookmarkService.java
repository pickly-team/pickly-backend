package org.pickly.service.bookmark.service.interfaces;


import org.pickly.service.bookmark.entity.Bookmark;

public interface BookmarkService {

  Bookmark findById(Long Id);

  void likeBookmark(Long memberId);

  void cancelLikeBookmark(Long memberId);
}
