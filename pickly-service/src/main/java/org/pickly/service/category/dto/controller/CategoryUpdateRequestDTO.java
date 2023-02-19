package org.pickly.service.category.dto.controller;

import jakarta.validation.constraints.Positive;

public record CategoryUpdateRequestDTO(
    @Positive
    Long id,
    Boolean isAutoDeleteMode,
    String name,
    String emoji
) {
}
