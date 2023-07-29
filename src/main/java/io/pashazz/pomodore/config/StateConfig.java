package io.pashazz.pomodore.config;


import io.pashazz.pomodore.services.UserService;
import io.pashazz.pomodore.services.state.RelaxTaskState;
import io.pashazz.pomodore.services.state.WorkTaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateConfig {

  @Autowired
  private UserService userService;


  // TODO Multithreaded implications of this initialization?
  private final WorkTaskState workTaskState = new WorkTaskState();
  private final RelaxTaskState relaxTaskState =  new RelaxTaskState();

  @Bean
  public WorkTaskState workTaskState() {
    workTaskState.setUserService(userService);
    workTaskState.setNextState(relaxTaskState);
    return workTaskState;
  }

  @Bean
  public RelaxTaskState relaxTaskState() {
    relaxTaskState.setUserService(userService);
    relaxTaskState.setNextState(workTaskState);
    return relaxTaskState;
  }

}
