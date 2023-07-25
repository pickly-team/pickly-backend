package org.pickly.service.comment.common;

import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.controller.request.CommentUpdateReq;
import org.pickly.service.comment.controller.response.BookmarkCommentRes;
import org.pickly.service.comment.controller.response.MemberCommentRes;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentUpdateDTO;
import org.pickly.service.comment.service.dto.BookmarkCommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  public MemberCommentRes toMemberCommentsResponse(BookmarkCommentDTO dto) {
    return MemberCommentRes.builder()
        .id(dto.getId())
        .member(dto.getMember())
        .bookmark(dto.getBookmark())
        .profileEmoji(dto.getProfileEmoji())
        .category(dto.getCategory())
        .isOwnerComment(dto.getIsOwnerComment())
        .content(dto.getContent())
        .createdTimestamp(dto.getCreatedTimestamp())
        .build();
  }

  public BookmarkCommentRes toBookmarkCommentsResponse(BookmarkCommentDTO dto) {
    return BookmarkCommentRes.builder()
        .id(dto.getId())
        .member(dto.getMember())
        .memberId(dto.getMemberId())
        .profileEmoji(dto.getProfileEmoji())
        .bookmark(dto.getBookmark())
        .category(dto.getCategory())
        .isOwnerComment(dto.getIsOwnerComment())
        .content(dto.getContent())
        .createdTimestamp(dto.getCreatedTimestamp())
        .build();
  }

  public BookmarkCommentDTO toBookmarkCommentDTO(Comment comment) {
    return new BookmarkCommentDTO(comment);
  }

  public CommentCreateDTO toCreateDTO(CommentCreateReq req) {
    return new CommentCreateDTO(req.getContent());
  }

  public CommentUpdateDTO toUpdateDTO(CommentUpdateReq req) {
    return new CommentUpdateDTO(req.getContent());
  }

}
