package org.pickly.service.category.controller.response;

public record CategoryResponseDTO(
  long categoryId,
  String name,
  String emoji
  ){
}
