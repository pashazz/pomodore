package io.pashazz.pomodore.services.state;

import io.pashazz.pomodore.entities.Task;

public interface TaskState {

  TaskState nextState(Task task);

}
