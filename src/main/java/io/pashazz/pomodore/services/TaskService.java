package io.pashazz.pomodore.services;

import io.pashazz.pomodore.entities.Phase;
import io.pashazz.pomodore.entities.Status;
import io.pashazz.pomodore.entities.Task;
import io.pashazz.pomodore.entities.User;
import io.pashazz.pomodore.repositories.TaskRepository;
import io.pashazz.pomodore.services.state.TaskState;
import io.pashazz.pomodore.services.state.TaskStateManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
public class TaskService {


  @Autowired
  private TaskRepository repo;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskStateManager taskStateManager;



  @Transactional
  public List<Task> getExistingTaskForUser(long chatId) {
    return repo.getTasksByChatId(chatId);
  }

  @Transactional
  public Task addNewTask(long chatId, Phase phase) {
    User user = userService.getOrDefault(chatId);
    Task task = new Task();
    task.setChatId(chatId);
    task.setPhase(phase);
    task.setTime(calculateTimeFor(user, phase));
    task.setStatus(Status.STARTED);
    return repo.save(task);

  }

  protected long calculateTimeFor(User user, Phase phase) {
    Instant now = Instant.now();
    Instant fireTime = now.plusSeconds(phase.getLength(user));
    return fireTime.getEpochSecond();
  }

  @Transactional
  public Iterable<Task> getTasksForTime(Instant time) {
    return repo.getTasksByTime(time.getEpochSecond());
  }




  @Transactional
  public Task changeStateForTask(Task task) {
    TaskState taskState = taskStateManager.taskStateForTask(task);
    TaskState next = taskState.nextState(task);
    log.debug(String.format(
      "[changeStateForTask] next state: %s; next execution time: %s", task.getPhase(),
      LocalDateTime.ofEpochSecond(task.getTime(), 0, ZoneOffset.UTC)));
    return repo.save(task);
  }

  @Transactional
  public void removeTask(Task task) {
    repo.delete(task);
  }
}
