package org.pickly.service.domain.category.controller.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.common.utils.validator.emoji.EmojiCheck;

public record CategoryRequestDTO(
    @Length(max = 20, message = "카테고리 이름은 20글자가 최대입니다.")
    @NotBlank(message = "카테고리 이름을 입력해주세요.")
    String name,

    @EmojiCheck
    @NotBlank(message = "카테고리 emoji를 입력해주세요.")
    String emoji
){
}
