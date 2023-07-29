package io.pashazz.pomodore.services;

import io.pashazz.pomodore.entities.Task;

public interface MessagePublishingService {
  void publishChangeStatusTextMessage(Task task);

}
