package org.pickly.service.comment.common;

import java.time.format.DateTimeFormatter;
import org.pickly.service.comment.controller.response.CommentRes;
import org.pickly.service.comment.entity.Comment;
import org.pickly.service.comment.service.dto.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

  public CommentRes toResponse(CommentDTO dto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    return CommentRes.builder()
        .id(dto.getId())
        .member(dto.getMember())
        .isOwnerComment(dto.getIsOwnerComment())
        .content(dto.getContent())
        .createdDateTime(dto.getCreatedDateTime().format(formatter))
        .build();
  }

  public CommentDTO toDTO(Comment comment) {
    return CommentDTO.builder()
        .id(comment.getId())
        .member(comment.getMember().getNickname())
        .isOwnerComment(comment.getIsOwnerComment())
        .content(comment.getContent())
        .createdDateTime(comment.getCreatedAt())
        .build();
  }

}
