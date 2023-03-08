package org.pickly.service.comment.service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentDTO {

  private Long id;
  private String member;
  private Boolean isOwnerComment;
  private String content;
  private LocalDateTime createdDateTime;

}
