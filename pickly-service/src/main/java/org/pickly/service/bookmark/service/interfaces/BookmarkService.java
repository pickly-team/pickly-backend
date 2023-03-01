package org.pickly.service.bookmark.service.interfaces;


public interface BookmarkService {

  void likeBookmark(Long memberId);

  void cancelLikeBookmark(Long memberId);

}
