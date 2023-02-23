package org.pickly.service.bookmark.repository.interfaces;

import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  // FIXME: 유저의 북마크 관련 기능이 많은데, 걍 Bookmark에 MemberId 넣는게 좋지 않을까?
  @Query("select count(b) from Bookmark b "
      + "inner join b.category c "
      + "where c.member.id = :memberId and b.isUserLike = true")
  Long countMemberLikes(@Param("memberId") Long memberId);

}
