package org.pickly.service.comment.repository.interfaces;

import java.util.List;
import org.pickly.service.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("select c from Comment c join fetch c.member where c.bookmark.id = :bookmarkId")
  List<Comment> findByBookmark(@Param("bookmarkId") Long bookmarkId);

  Long countAllByMemberId(Long memberId);

}
