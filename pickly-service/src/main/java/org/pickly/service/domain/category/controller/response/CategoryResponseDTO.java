package org.pickly.service.domain.category.controller.response;

public record CategoryResponseDTO(
  long categoryId,
  String name,
  String emoji
  ){
}
