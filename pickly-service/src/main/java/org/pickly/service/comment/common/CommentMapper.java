package org.pickly.service.comment.common;

import org.pickly.service.comment.controller.request.CommentCreateReq;
import org.pickly.service.comment.controller.response.CommentRes;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentCreateDTO;
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

  public CommentCreateDTO toCreateDTO(CommentCreateReq req) {
    return new CommentCreateDTO(req.getContent());
  }

}
