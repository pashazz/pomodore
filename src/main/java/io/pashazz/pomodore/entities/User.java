package io.pashazz.pomodore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
  /**
   * This field is considered the same as chatId
   *
   */
  @Id
  @Column(name="user_id")
  long userId;

  @Column(nullable = false, name = "work_phase_length")
  int workPhaseLengthSec;

  @Column(nullable = false, name = "relax_phase_length")
  int relaxPhaseLengthSec;
}
