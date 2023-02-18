package org.pickly.service.category.dto.controller;

import jakarta.validation.constraints.NotEmpty;

public record CategoryRequestDTO(
    @NotEmpty
    Long memberId,
    @NotEmpty
    String name
){
}
