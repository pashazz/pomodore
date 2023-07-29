package io.pashazz.pomodore.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
public class Task {
  @Id
  @GeneratedValue
  private UUID id;

  @Column(name="time", nullable=false,unique = false)
  private long time;

  @Column(name="chat_id", nullable = false, unique = false)
  private long chatId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Phase phase;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;




}
