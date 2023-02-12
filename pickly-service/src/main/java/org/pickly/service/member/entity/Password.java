package org.pickly.service.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

  @Column(name = "password", nullable = false)
  private String value;

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
