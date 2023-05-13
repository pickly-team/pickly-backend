package org.pickly.service.category.dto.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.pickly.service.common.utils.validator.EmojiCheck;

public record CategoryRequestDTO(
    @Positive(message = "멤버 ID는 양수입니다.")
    Long memberId,
    @NotEmpty(message = "카테고리 이름을 입력해주세요.")
    String name,

    @EmojiCheck
    @NotEmpty(message = "카테고리 emoji를 입력해주세요.")
    String emoji
){
}
