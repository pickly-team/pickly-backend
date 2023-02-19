package org.pickly.service.category.dto.controller;

public record CategoryResponseDTO(
  long categoryId,
  long memberId,
  String name
  ){
}
