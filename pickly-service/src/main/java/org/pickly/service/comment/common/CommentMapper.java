package org.pickly.service.comment.common;

import org.pickly.service.comment.controller.response.CommentRes;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  public CommentRes toResponse(CommentDTO dto) {
    return new CommentRes(dto);
  }

  public CommentDTO toDTO(Comment comment) {
    return new CommentDTO(comment);
  }

}
