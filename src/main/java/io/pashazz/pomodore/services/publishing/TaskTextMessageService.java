package io.pashazz.pomodore.services.publishing;

import io.pashazz.pomodore.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskTextMessageService {

  public String getChangeStatusTextMessage(Task task) {
    String message = switch (task.getPhase()) {
      case WORK -> "Time to get to work!";
      case RELAX -> "Time to relax!";
    };
    return message;
  }

}
