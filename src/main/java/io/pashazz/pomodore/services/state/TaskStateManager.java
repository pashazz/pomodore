package io.pashazz.pomodore.services.state;

import io.pashazz.pomodore.entities.Phase;
import io.pashazz.pomodore.entities.Status;
import io.pashazz.pomodore.entities.Task;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TaskStateManager {

  @Autowired
  private WorkTaskState workTaskState;

  @Autowired
  private RelaxTaskState relaxTaskState;

  private Map<Phase, TaskState> phaseMap;

  @PostConstruct
  void init() {
    phaseMap = new HashMap<>();
    phaseMap.put(Phase.WORK, workTaskState);
    phaseMap.put(Phase.RELAX, relaxTaskState);
  }

  public TaskState taskStateForTask(Task task) {
    return phaseMap.get(task.getPhase());
  }

}
