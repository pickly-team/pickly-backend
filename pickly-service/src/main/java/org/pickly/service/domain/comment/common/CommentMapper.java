package org.pickly.service.domain.comment.common;

import org.pickly.service.domain.comment.controller.request.CommentCreateReq;
import org.pickly.service.domain.comment.controller.request.CommentUpdateReq;
import org.pickly.service.domain.comment.controller.response.BookmarkCommentRes;
import org.pickly.service.domain.comment.controller.response.MemberCommentRes;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.service.dto.CommentCreateDTO;
import org.pickly.service.domain.comment.service.dto.CommentDTO;
import org.pickly.service.domain.comment.service.dto.CommentUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  public MemberCommentRes toMemberCommentsResponse(CommentDTO dto) {
    return MemberCommentRes.builder()
        .id(dto.getId())
        .member(dto.getMember())
        .bookmarkId(dto.getBookmarkId())
        .bookmark(dto.getBookmark())
        .profileEmoji(dto.getProfileEmoji())
        .category(dto.getCategory())
        .isOwnerComment(dto.getIsOwnerComment())
        .content(dto.getContent())
        .createdTimestamp(dto.getCreatedTimestamp())
        .build();
  }

  public BookmarkCommentRes toBookmarkCommentsResponse(CommentDTO dto, Long memberId) {
    return BookmarkCommentRes.builder()
        .id(dto.getId())
        .member(dto.getMember())
        .memberId(dto.getMemberId())
        .profileEmoji(dto.getProfileEmoji())
        .bookmark(dto.getBookmark())
        .category(dto.getCategory())
        .isOwnerComment(dto.getMemberId().equals(memberId))
        .content(dto.getContent())
        .createdTimestamp(dto.getCreatedTimestamp())
        .build();
  }

  public CommentDTO toBookmarkCommentDTO(Comment comment) {
    return new CommentDTO(comment);
  }

  public CommentCreateDTO toCreateDTO(CommentCreateReq req) {
    return new CommentCreateDTO(req.getContent());
  }

  public CommentUpdateDTO toUpdateDTO(CommentUpdateReq req) {
    return new CommentUpdateDTO(req.getContent());
  }

}
