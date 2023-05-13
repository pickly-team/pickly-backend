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
  private long memberId;
  private String name;

  public static CategoryDTO from(final Category category) {
    return CategoryDTO.builder()
        .categoryId(category.getId())
        .memberId(category.getMember().getId())
        .name(category.getName())
        .build();
  }
}
