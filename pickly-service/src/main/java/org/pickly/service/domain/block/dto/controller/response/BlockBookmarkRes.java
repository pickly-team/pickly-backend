package org.pickly.service.domain.block.dto.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockBookmarkRes {

  private Long id;
  private String title;
  private String previewImageUrl;

  public static BlockBookmarkRes of(Long id, String title, String previewImageUrl) {
    return new BlockBookmarkRes(id, title, previewImageUrl);
  }
}
