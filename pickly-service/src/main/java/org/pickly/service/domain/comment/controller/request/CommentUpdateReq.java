package org.pickly.service.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentUpdateReq {

  @Schema(description = "댓글 내용", example = "와! 정말 훌륭해!")
  @Length(min = 1, max = 150, message = "댓글은 최대 150자까지 입력할 수 있습니다.")
  @NotBlank(message = "댓글 내용을 입력해주세요.")
  private String content;

}
