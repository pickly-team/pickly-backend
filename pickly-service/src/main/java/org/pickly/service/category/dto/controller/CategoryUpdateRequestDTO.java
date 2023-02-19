package org.pickly.service.category.dto.controller;

public record CategoryUpdateRequestDTO(
    Boolean isAutoDeleteMode,
    String name,
    String emoji
) {
}
