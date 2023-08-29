package ua.hryhorenko.telegrambotvacancies;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class VacanciesBot extends TelegramLongPollingBot {
  public VacanciesBot() {
    super("6148636781:AAGABws8jqh8eSj21p4guKih147171EzGnc");
  }

  @Override
  public void onUpdateReceived(Update update) {
    String text = update.getMessage().getText();
    System.out.println(text);
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(update.getMessage().getChatId());
    sendMessage.setText("Your text : " + text);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getBotUsername() {
    return "hryhorenko_vacancies_bot";
  }
}
