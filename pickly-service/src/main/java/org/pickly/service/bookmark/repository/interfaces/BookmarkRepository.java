package org.pickly.service.bookmark.repository.interfaces;

import java.util.List;
import java.util.Optional;
import org.pickly.service.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  @Query("select b from Bookmark b join fetch b.member m where b.id = :id")
  Optional<Bookmark> findOneById(@Param("id") Long id);

  @Query("select b from Bookmark b join fetch b.category c where b.id = :id")
  Optional<Bookmark> findByIdWithCategory(@Param("id") Long id);

  Long countByMemberId(Long memberId);

  @Modifying
  @Query("DELETE FROM Bookmark b WHERE b.id = :bookmarkId")
  Integer deleteBookmarkById(@Param("bookmarkId") Long bookmarkId);

  @Modifying(clearAutomatically = true)
  @Query("DELETE FROM Bookmark b WHERE b.id IN :bookmarkIds")
  Integer deleteBookmarksByIds(@Param("bookmarkIds") List<Long> bookmarkIds);
}
