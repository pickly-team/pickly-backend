package org.pickly.service.domain.category.dto.controller.request;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.common.utils.validator.emoji.EmojiCheck;

public record CategoryUpdateRequestDTO(

    @Length(max = 10, message = "카테고리 이름은 20글자가 최대입니다.")
    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    String name,

    @EmojiCheck
    @NotEmpty(message = "카테고리 emoji를 입력해주세요.")
    String emoji
    
) {
}
