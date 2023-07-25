package org.pickly.service.comment.common;

import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.controller.request.CommentUpdateReq;
import org.pickly.service.comment.controller.response.CommentRes;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.pickly.service.comment.service.dto.CommentUpdateDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  public CommentRes toResponse(CommentDTO dto) {
    return CommentRes.builder()
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

  public CommentDTO toDTO(Comment comment) {
    return new CommentDTO(comment);
  }

  public CommentCreateDTO toCreateDTO(CommentCreateReq req) {
    return new CommentCreateDTO(req.getContent());
  }

  public CommentUpdateDTO toUpdateDTO(CommentUpdateReq req) {
    return new CommentUpdateDTO(req.getContent());
  }

}
