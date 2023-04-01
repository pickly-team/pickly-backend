package org.pickly.service.comment.repository.interfaces;

import org.pickly.service.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Long countAllByMemberId(Long memberId);

}
