package org.pickly.service.block.controller.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlockBookmarkRes {

  private List<BlockBookmark> data;

  public static BlockBookmarkRes of(List<BlockBookmark> blockBookmarks) {
    return new BlockBookmarkRes(blockBookmarks);
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BlockBookmark {

    private Long id;
    private String title;
    private String previewImageUrl;

    public static BlockBookmark of(Long id, String title, String previewImageUrl) {
      return new BlockBookmark(id, title, previewImageUrl);
    }
  }

}
