package org.pickly.service.bookmark.service.interfaces;


public interface BookmarkService {

  void likeBookmark(Long memberId);

  void unlikeBookmark(Long memberId);

}
