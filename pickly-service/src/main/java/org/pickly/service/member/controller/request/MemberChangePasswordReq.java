package org.pickly.service.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@Schema(description = "Member password change request")
public class MemberChangePasswordReq {

  @NotNull(message = "현재 비밀번호를 입력해주세요.")
  @Schema(description = "current password", example = "currentPassword")
  private String currentPassword;

  @NotBlank(message = "새 비밀번호를 입력해주세요.")
  @Length(min = 8, max = 32, message = "새 비밀번호는 8자 이상 32자 이하로 입력해야 합니다.")
  @Schema(description = "new password", example = "newPassword")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=\\S+$).{8,32}$",
      message = "새 비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
  private String newPassword;
}
