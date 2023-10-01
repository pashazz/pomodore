package io.pashazz.pomodore.bot;

import io.pashazz.pomodore.config.TgConfig;
import io.pashazz.pomodore.dto.MessageParameters;
import io.pashazz.pomodore.entities.Phase;
import io.pashazz.pomodore.entities.Task;
import io.pashazz.pomodore.entities.User;
import io.pashazz.pomodore.services.TaskService;
import io.pashazz.pomodore.services.UserService;
import io.pashazz.pomodore.services.publishing.TaskTextMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
@Component
public class PomodoreTgBot extends TelegramLongPollingBot {

  private TgConfig config;

  private UserService userService;

  private TaskService taskService;

  private TaskTextMessageService taskTextMessageService;




  public PomodoreTgBot(TgConfig config, UserService userService, TaskService taskService, TaskTextMessageService taskTextMessageService) {
    super(config.getToken());
    this.config = config;
    this.userService = userService;
    this.taskService = taskService;
    this.taskTextMessageService = taskTextMessageService;
    log.info("Initialized Telegram bot with token: " + config.getToken() );
  }

  @Override
  public void onUpdateReceived(Update update) {
    Message message;
    log.debug("[onUpdateReceived] thread: " + Thread.currentThread().getName());
    if (update.hasMessage()) {
      message =  update.getMessage();
    } else if (update.hasCallbackQuery()) {
      message = update.getCallbackQuery().getMessage();
    } else {
      log.info("Received update: " + update + " not a message");
      return;
    }
    onMessage(message);
  }

  //Возможные команды:
  //1. /newTimer
  //2. /status: returns: work (took 10 minutes, 15 minutes left)
  //3. /pause (returns as status and stops)
  //4. /continue (returns as status and continues)
  private void onMessage(Message message) {
    if (!message.hasText()) {
      log.warn("[onMessage] Message received for " + message.getChatId() + " does not appear to have text. Exiting");
      return;
    }
    String text = message.getText();
    String[] args = text.split("\\s");
    if (args.length < 1) {
      log.warn("[onMessage] Invalid text: " + args + " Exiting");
      return;
    }
    String command = args[0];
    if (command.equals("/newTimer")) {
      onNewTimer(message.getChatId(), args);
    }
  }

  private void onNewTimer(long chatId, String[] args) {
    // Ignore args for now
    List<Task> existing = taskService.getExistingTaskForUser(chatId);

    if (!existing.isEmpty()) {
      for (var task: existing) {
        taskService.removeTask(task);
      }
    }

    log.info(String.format("[onNewTimer] new time for chat id %d", chatId));
    publishChangeStatusTextMessage(taskService.addNewTask(chatId, Phase.WORK));
  }


  public void publishChangeStatusTextMessage(Task task) {

    MessageParameters parameters = new MessageParameters(task.getChatId(),
      taskTextMessageService.getChangeStatusTextMessage(task));
    log.debug("[publishStatusTextMessage] task: " + task.getChatId() + " -  phase changed to: " + task.getPhase());
    sendMessage(parameters);
  }


  public void sendMessage(MessageParameters parameters) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText(parameters.message());
    sendMessage.setChatId(parameters.chatId());
    sendApiMethodAsync(sendMessage)
      .exceptionallyAsync(ex -> {
        log.warn("Exception while executing sendMessage: " + ex);
        return null;
      });
  }



  @Override
  public String getBotUsername() {
    return config.getBotName();
  }

  @Override
  public void onRegister() {
    super.onRegister();
  }
}
