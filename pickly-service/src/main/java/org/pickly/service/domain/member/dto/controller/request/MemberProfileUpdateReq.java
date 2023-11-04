package org.pickly.service.domain.member.dto.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.pickly.service.common.utils.validator.nickname.NicknameCheck;

@Getter
@AllArgsConstructor
@Schema(description = "Member profile update request")
public class MemberProfileUpdateReq {

  @NotBlank(message = "사용자 이름을 입력해주세요.")
  @Length(max = 20, message = "사용자 이름은 20글자 이하로 입력해야 합니다.")
  @Schema(description = "member name", example = "John Doe")
  private String name;

  @NicknameCheck
  @NotBlank(message = "사용자 닉네임을 입력해주세요.")
  @Length(max = 7, message = "사용자 닉네임은 7글자 이하로 입력해야 합니다.")
  @Schema(description = "member nickname", example = "johndoe")
  private String nickname;

  @NotBlank
  @Schema(description = "member profile emoji", example = "👨🏻‍💻")
  private String profileEmoji;

}
