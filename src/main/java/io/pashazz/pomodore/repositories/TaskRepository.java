package io.pashazz.pomodore.repositories;

import io.pashazz.pomodore.entities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
  Iterable<Task> getTasksByTime(long time);

  List<Task> getTasksByChatId(long chatId);
}
