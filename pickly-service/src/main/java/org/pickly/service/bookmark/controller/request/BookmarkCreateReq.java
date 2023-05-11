package org.pickly.service.bookmark.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.bookmark.entity.Visibility;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkCreateReq {

  @Positive
  private Long categoryId;

  @Positive
  private Long memberId;

  @NotBlank(message = "URL을 입력해주세요")
  private String url;

  @Length(min = 1, max = 100, message = "최대 100자까지 입력할 수 있습니다.")
  @NotBlank(message = "제목을 입력해주세요")
  private String title;

  @Length(min = 1, max = 2000, message = "최대 2000자까지 입력할 수 있습니다.")
  @NotBlank(message = "프리뷰 이미지 URL을 입력해주세요")
  private String previewImageUrl;

  @NotNull(message = "공개 범위 표시 정보는 필수입니다. ")
  private Visibility visibility;

}