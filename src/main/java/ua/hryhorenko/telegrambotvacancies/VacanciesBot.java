package ua.hryhorenko.telegrambotvacancies;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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
    sendMessage.setText("Welcome to vacancies bot! Please, choose your title:");
    sendMessage.setReplyMarkup(getStartMenu());
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }

  private ReplyKeyboard getStartMenu() {
    List<InlineKeyboardButton> row = new ArrayList<>();
    InlineKeyboardButton junior = new InlineKeyboardButton();
    junior.setText("Junior");
    junior.setCallbackData("showJuniorVacancies");
    row.add(junior);

    InlineKeyboardButton middle = new InlineKeyboardButton();
    middle.setText("Middle");
    middle.setCallbackData("showMiddleVacancies");
    row.add(middle);

    InlineKeyboardButton senior = new InlineKeyboardButton();
    senior.setText("Senior");
    senior.setCallbackData("showSeniorVacancies");
    row.add(senior);

    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    keyboard.setKeyboard(List.of(row));

    return keyboard;
  }

  @Override
  public String getBotUsername() {
    return "hryhorenko_vacancies_bot";
  }
}
