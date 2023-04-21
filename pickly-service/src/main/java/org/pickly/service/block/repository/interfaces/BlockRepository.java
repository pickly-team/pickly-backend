package org.pickly.service.block.repository.interfaces;

import org.pickly.service.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlockRepository extends JpaRepository<Block, Long> {

  boolean existsByBlockerIdAndBlockeeId(Long blockerId, Long blockeeId);

  boolean existsByBlockerIdAndBookmarkId(Long blockerId, Long bookmarkId);

  @Modifying
  @Query("delete from Block b where b.blocker.id = :blockerId and b.blockee.id = :blockeeId")
  void unBlockMember(@Param("blockerId") Long blockerId, @Param("blockeeId") Long blockeeId);

  @Modifying
  @Query("delete from Block b where b.blocker.id = :blockerId and b.bookmark.id = :bookmarkId")
  void unBlockBookmark(@Param("blockerId") Long blockerId, @Param("bookmarkId") Long bookmarkId);

}