package io.pashazz.pomodore.services.state;

import io.pashazz.pomodore.entities.Phase;
import io.pashazz.pomodore.entities.Task;
import io.pashazz.pomodore.repositories.TaskRepository;
import io.pashazz.pomodore.services.UserService;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class RelaxTaskState extends SpawningTaskState {
  private UserService userService;

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected Task applyChanges(Task task) {
    task.setPhase(Phase.RELAX);
    var user = userService.getUser(task.getChatId());
    long length = user.getWorkPhaseLengthSec();
    task.setTime(Instant.now().plus(Duration.of(length, ChronoUnit.SECONDS)).getEpochSecond());
    return task;
  }
}
