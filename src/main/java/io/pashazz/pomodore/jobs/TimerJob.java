package io.pashazz.pomodore.jobs;


import io.pashazz.pomodore.bot.PomodoreTgBot;
import io.pashazz.pomodore.entities.Task;
import io.pashazz.pomodore.repositories.TaskRepository;
import io.pashazz.pomodore.services.MessagePublishingService;
import io.pashazz.pomodore.services.TaskService;
import io.pashazz.pomodore.services.state.TaskState;
import io.pashazz.pomodore.services.state.TaskStateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Slf4j
public class TimerJob {

  @Autowired
  private TaskService taskService;

  @Autowired
  private PomodoreTgBot bot;


  @Scheduled(cron = "0/1 * * * * ?")
  public void doTimer() {
    Instant now = Instant.now();
    Iterable<Task> tasks = taskService.getTasksForTime(now);
    log.trace("[doTimer] time: " + now);
    for (Task task : tasks) {
      taskService.changeStateForTask(task);
      bot.publishChangeStatusTextMessage(task);
    }
  }


}
