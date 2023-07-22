package org.pickly.service.common.utils.base;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.pickly.service.common.utils.timezone.TimezoneHandler;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@Where(clause = "deleted_at is null")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(updatable = false, name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = TimezoneHandler.getNowByZone();
    this.updatedAt = TimezoneHandler.getNowByZone();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = TimezoneHandler.getNowByZone();
  }

  public boolean isDeleted() {
    return null != this.deletedAt;
  }

  public void delete() {
    this.deletedAt = TimezoneHandler.getNowByZone();
  }

  public void undoDelete() {
    this.deletedAt = null;
  }

}
