package org.pickly.service.category.dto.controller;

import jakarta.validation.constraints.NotEmpty;
import org.pickly.service.common.utils.validator.EmojiCheck;

public record CategoryRequestDTO(
    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    String name,

    @EmojiCheck
    @NotEmpty(message = "카테고리 emoji를 입력해주세요.")
    String emoji
){
}
