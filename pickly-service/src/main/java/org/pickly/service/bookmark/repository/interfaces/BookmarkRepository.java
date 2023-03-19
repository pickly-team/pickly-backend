package org.pickly.service.bookmark.repository.interfaces;

import java.util.List;
import java.util.Optional;
import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  Optional<Bookmark> findOneById(Long id);

  @Modifying
  @Query("DELETE FROM Bookmark b WHERE b.id = :bookmarkId")
  Integer deleteBookmarkById(@Param("bookmarkId") Long bookmarkId);

  @Modifying(clearAutomatically = true)
  @Query("DELETE FROM Bookmark b WHERE b.id IN :bookmarkIds")
  Integer deleteBookmarksByIds(@Param("bookmarkIds") List<Long> bookmarkIds);
}
