package org.pickly.service.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

  @Column(name = "password", nullable = false)
  private String value;

  @Builder
  public Password(final String inputPassword) {
    this.value = create(inputPassword);
  }

  public boolean isMatched(final String inputPassword) {
    return new BCryptPasswordEncoder().matches(inputPassword, this.value);
  }

  public String create(final String password) {
    return new BCryptPasswordEncoder().encode(password);
  }

  public void update(final String newPassword) {
    this.value = create(newPassword);
  }

}
