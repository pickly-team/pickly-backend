package org.pickly.service.bookmark.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.pickly.service.bookmark.entity.Visibility;
import org.pickly.service.common.utils.validator.url.UrlCheck;
import org.pickly.service.common.utils.validator.visibility.VisibilityCheck;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkCreateReq {

  @Positive
  private Long categoryId;

  @Positive
  private Long memberId;

  @UrlCheck
  @NotBlank(message = "URL을 입력해주세요")
  private String url;

  @VisibilityCheck
  @NotNull(message = "공개 범위 표시 정보는 필수입니다. ")
  private Visibility visibility;

}
