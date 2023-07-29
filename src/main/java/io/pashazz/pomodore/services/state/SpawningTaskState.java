package io.pashazz.pomodore.services.state;

import io.pashazz.pomodore.entities.Task;

public abstract class SpawningTaskState implements TaskState {


  private SpawningTaskState next;

  public void setNextState(SpawningTaskState next) {
    this.next = next;
  }

  @Override
  public TaskState nextState(Task task) {
    next.applyChanges(task);
    return next;
  }

  protected abstract Task applyChanges(Task task);


}
