package org.pickly.service.category.dto.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.pickly.service.category.entity.Category;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDTO {

  private long categoryId;
  private String name;
  private String emoji;
  private Integer orderNum;

  public static CategoryDTO from(final Category category) {
    return CategoryDTO.builder()
        .categoryId(category.getId())
        .name(category.getName())
        .emoji(category.getEmoji())
        .orderNum(category.getOrderNum())
        .build();
  }
}
