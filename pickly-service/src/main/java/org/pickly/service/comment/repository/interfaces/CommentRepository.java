package org.pickly.service.comment.repository.interfaces;

import org.pickly.service.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Long countAllByMemberIdAndDeletedAtNull(Long memberId);

  @Query("select c from Comment c join fetch c.member where c.id = :id and c.deletedAt is null")
  Optional<Comment> findByIdAndDeletedAtNull(@Param("id") Long id);
}
