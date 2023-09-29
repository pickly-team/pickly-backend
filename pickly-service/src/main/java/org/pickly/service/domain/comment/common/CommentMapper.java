package org.pickly.service.domain.comment.common;

import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.pickly.service.domain.comment.dto.controller.request.CommentCreateReq;
import org.pickly.service.domain.comment.dto.controller.request.CommentUpdateReq;
import org.pickly.service.domain.comment.dto.controller.response.BookmarkCommentRes;
import org.pickly.service.domain.comment.dto.controller.response.MemberCommentRes;
import org.pickly.service.domain.comment.entity.Comment;
import org.pickly.service.domain.comment.dto.service.CommentCreateDTO;
import org.pickly.service.domain.comment.dto.service.CommentDTO;
import org.pickly.service.domain.comment.dto.service.CommentUpdateDTO;
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

  public BookmarkCommentRes toResponse(Comment comment, Long memberId) {
    String timezone = comment.getMember().getTimezone();
    return BookmarkCommentRes.builder()
        .id(comment.getId())
        .member(comment.getMember().getNickname())
        .memberId(comment.getMember().getId())
        .profileEmoji(comment.getMember().getProfileEmoji())
        .bookmark(comment.getBookmark().getTitle())
        .category(comment.getBookmark().getCategory().getName())
        .isOwnerComment(comment.getMember().getId().equals(memberId))
        .content(comment.getContent())
        .createdTimestamp(TimezoneHandler.convertToUnix(comment.getCreatedAt(), timezone))
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
