package org.pickly.service.domain.bookmark.dto.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.domain.bookmark.entity.Visibility;
import org.pickly.service.common.utils.validator.url.UrlCheck;

public record ExtensionBookmarkReq() {

  public record CreateDto(
      @Positive Long categoryId,

      @NotBlank String memberId,

      @UrlCheck @NotBlank(message = "URL을 입력해주세요")
      String url,

      @Length(max = 100, message = "제목은 최대 100글자 까지만 입력할 수 있습니다.")
      String title,

      String thumbnail,

      @NotNull(message = "공개 범위 표시 정보는 필수입니다. ")
      Visibility visibility
  ) {
  }

}
