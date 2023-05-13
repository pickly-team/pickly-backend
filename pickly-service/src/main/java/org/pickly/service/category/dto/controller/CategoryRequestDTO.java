package org.pickly.service.category.dto.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record CategoryRequestDTO(
    @Positive
    Long memberId,
    @NotEmpty
    String name
){
}
