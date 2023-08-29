package ua.hryhorenko.telegrambotvacancies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.hryhorenko.telegrambotvacancies.dto.VacancyDto;
import ua.hryhorenko.telegrambotvacancies.service.VacancyService;

import java.util.ArrayList;
import java.util.List;

@Component
public class VacanciesBot extends TelegramLongPollingBot {
  @Autowired
  private VacancyService vacancyService;

  public VacanciesBot() {
    super("6148636781:AAGABws8jqh8eSj21p4guKih147171EzGnc");
  }

  @Override
  public void onUpdateReceived(Update update) {
    try {
      if (update.getMessage() != null) {
        handleStartCommand(update);
      }

      if (update.getCallbackQuery() != null) {
        String callbackData = update.getCallbackQuery().getData();

        if ("showJuniorVacancies".equals(callbackData)) {
          showJuniorVacancies(update);
        } else if ("showMiddleVacancies".equals(callbackData)) {
          showMiddleVacancies(update);
        } else if ("showSeniorVacancies".equals(callbackData)) {
          showSeniorVacancies(update);
        } else if (callbackData.startsWith("vacancyId=")) {
          String id = callbackData.split("=")[1];
          showVacancyDescription(id, update);
        }
      }
    } catch (Exception ex) {
        throw new RuntimeException("Can't sent message to user", ex);
    }
  }

  private void showVacancyDescription(String id, Update update) throws TelegramApiException {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
    VacancyDto vacancy = vacancyService.get(id);
    String description = vacancy.getShortDescription();
    sendMessage.setText(description);
    execute(sendMessage);
  }

  private void showJuniorVacancies(Update update) throws TelegramApiException {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Please choose vacancy:");
    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
    sendMessage.setReplyMarkup(getJuniorVacanciesMenu());
    execute(sendMessage);
  }

  private void showMiddleVacancies(Update update) throws TelegramApiException {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Please choose vacancy:");
    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
    sendMessage.setReplyMarkup(getMIddleVacanciesMenu());
    execute(sendMessage);
  }

  private void showSeniorVacancies(Update update) throws TelegramApiException {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setText("Please choose vacancy:");
    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
    sendMessage.setReplyMarkup(getSeniorVacanciesMenu());
    execute(sendMessage);
  }

  private ReplyKeyboard getJuniorVacanciesMenu() {
    List<InlineKeyboardButton> row = new ArrayList<>();
    List<VacancyDto> vacancies = vacancyService.getJuniorVacancies();

    for (VacancyDto vacancy : vacancies) {
      InlineKeyboardButton vacancyButton = new InlineKeyboardButton();
      vacancyButton.setText(vacancy.getTitle());
      vacancyButton.setCallbackData("vacancyId=" + vacancy.getId());
      row.add(vacancyButton);
    }

    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    keyboard.setKeyboard(List.of(row));

    return keyboard;
  }

  private ReplyKeyboard getMIddleVacanciesMenu() {
    List<InlineKeyboardButton> row = new ArrayList<>();
    InlineKeyboardButton maVacancy = new InlineKeyboardButton();
    maVacancy.setText("Middle Java developer at MA");
    maVacancy.setCallbackData("vacancyId=3");
    row.add(maVacancy);

    InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
    googleVacancy.setText("Middle Java developer at Google");
    googleVacancy.setCallbackData("vacancyId=4");
    row.add(googleVacancy);

    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    keyboard.setKeyboard(List.of(row));

    return keyboard;
  }

  private ReplyKeyboard getSeniorVacanciesMenu() {
    List<InlineKeyboardButton> row = new ArrayList<>();
    InlineKeyboardButton maVacancy = new InlineKeyboardButton();
    maVacancy.setText("Senior Java developer at MA");
    maVacancy.setCallbackData("vacancyId=5");
    row.add(maVacancy);

    InlineKeyboardButton googleVacancy = new InlineKeyboardButton();
    googleVacancy.setText("Senior Java developer at Google");
    googleVacancy.setCallbackData("vacancyId=6");
    row.add(googleVacancy);

    InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
    keyboard.setKeyboard(List.of(row));

    return keyboard;
  }

  private void handleStartCommand(Update update) {
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
