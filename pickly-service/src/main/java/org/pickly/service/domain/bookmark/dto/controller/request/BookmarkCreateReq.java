package org.pickly.service.domain.bookmark.dto.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.common.utils.validator.url.UrlCheck;

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

  @Length(max = 100, message = "제목은 최대 100글자 까지만 입력할 수 있습니다.")
  private String title;

  private String thumbnail;

  @NotNull(message = "공개 범위 표시 정보는 필수입니다. ")
  private Visibility visibility;

  public BookmarkCreateReq(Long memberId, ExtensionBookmarkReq.CreateDto dto) {
    this.memberId = memberId;
    this.categoryId = dto.categoryId();
    this.url = dto.url();
    this.title = dto.title();
    this.thumbnail = dto.thumbnail();
    this.visibility = dto.visibility();
  }

}
