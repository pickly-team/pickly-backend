package org.pickly.service.comment;

import org.pickly.service.bookmark.entity.Bookmark;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.member.entity.Member;

public class CommentFactory {

  public Comment testComment(Member member, Bookmark bookmark, Boolean isOwnerComment, String content) {
    return new Comment(member, bookmark, isOwnerComment, content);
  }

}
