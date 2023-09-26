package org.pickly.service.domain.category.controller.request;

import jakarta.validation.constraints.NotBlank;
import org.pickly.service.common.utils.validator.emoji.EmojiCheck;

public record CategoryRequestDTO(
    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    String name,

    @EmojiCheck
    @NotBlank(message = "카테고리 emoji를 입력해주세요.")
    String emoji
){
}
