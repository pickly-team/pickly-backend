package org.pickly.service.comment;

import org.pickly.service.domain.bookmark.entity.Bookmark;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.member.entity.Member;

public class CommentFactory {

  public Comment testComment(
      Member member, Bookmark bookmark, Boolean isOwnerComment, String content
  ) {
    return new Comment(member, bookmark, isOwnerComment, content);
  }

}
