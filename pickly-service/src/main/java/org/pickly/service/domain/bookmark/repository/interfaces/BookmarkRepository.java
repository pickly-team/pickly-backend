package org.pickly.service.domain.bookmark.repository.interfaces;

import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

  @Query("select b from Bookmark b join fetch b.member m where b.id = :id and b.deletedAt is null")
  Optional<Bookmark> findOneById(@Param("id") Long id);

  @Query("select b from Bookmark b join fetch b.category c where b.id = :id and b.deletedAt is null")
  Optional<Bookmark> findByIdWithCategory(@Param("id") Long id);

  Long countByMemberIdAndDeletedAtNull(Long memberId);

  Long countByCategoryIdAndDeletedAtIsNull(Long categoryId);

  @Query("select count(b) from Bookmark b " +
      "where b.member.id = :memberId and b.readByUser = true and b.deletedAt is null")
  Long countReadBookmarksByMemberId(@Param("memberId") Long memberId);

  @Query("select count(b) from Bookmark b " +
      "where b.category.id = :categoryId and b.readByUser = true and b.deletedAt is null")
  Long countReadBookmarksByCategoryId(@Param("categoryId") Long categoryId);

  @Modifying
  @Query("update Bookmark b set b.deletedAt = :deletedAt WHERE b.id IN :bookmarkIds")
  void deleteBookmarksByIds(
      @Param("bookmarkIds") List<Long> bookmarkIds, @Param("deletedAt") LocalDateTime deletedAt
  );

  @Modifying
  @Query("update Bookmark b set b.deletedAt = :deletedAt WHERE b.category.id = :categoryId")
  void deleteByCategory(
      @Param("categoryId") Long categoryId, @Param("deletedAt") LocalDateTime deletedAt
  );

  @Modifying
  @Query("update Bookmark b set b.deletedAt = :deletedAt WHERE b.category.id in :categoryIds")
  void deleteByCategory(
      @Param("categoryIds") List<Long> categoryIds, @Param("deletedAt") LocalDateTime deletedAt
  );

  @Transactional
  @Modifying
  @Query("UPDATE Bookmark b SET b.readByUser=True WHERE b.id=:bookmarkId and b.member.id = :memberId")
  void readByUser(@Param("bookmarkId") Long id, @Param("memberId") Long memberId);

  @Query("select b from Bookmark b where b.readByUser = false and b.deletedAt is null")
  List<Bookmark> findAllUnreadBookmark();

}
