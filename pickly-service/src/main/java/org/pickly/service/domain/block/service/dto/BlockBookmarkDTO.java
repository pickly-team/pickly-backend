package org.pickly.service.domain.block.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockBookmarkDTO {

  private Long id;
  private String title;
  private String previewImageUrl;

}
