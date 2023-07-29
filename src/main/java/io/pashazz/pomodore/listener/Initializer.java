package io.pashazz.pomodore.listener;

import io.pashazz.pomodore.bot.PomodoreTgBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class Initializer {
  @Autowired
  private PomodoreTgBot bot;

  @EventListener({ContextRefreshedEvent.class})
  public void init() {
    try {
      TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
      api.registerBot(bot);
    } catch (TelegramApiException e) {
      log.error("Telegram API exception: " + e);
    }
  }

}
