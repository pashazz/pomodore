package io.pashazz.pomodore.entities;

public enum Phase {
  WORK,
  RELAX;
  public int getLength(User user) {
    if (this.equals(WORK)) {
      return user.getWorkPhaseLengthSec();
    } else {
      return user.getRelaxPhaseLengthSec();
    }
  }
}

