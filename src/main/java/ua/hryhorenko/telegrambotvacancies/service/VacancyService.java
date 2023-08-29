package ua.hryhorenko.telegrambotvacancies.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.hryhorenko.telegrambotvacancies.dto.VacancyDto;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class VacancyService {
  @Autowired
  private VacanciesReaderService vacanciesReaderService;

  private final Map<String, VacancyDto> vacancies = new HashMap<>();

  @PostConstruct
  public void init() {
    List<VacancyDto> list = vacanciesReaderService.getVacanciesFromFile("vacancies.csv");

    for (VacancyDto vacancy : list) {
      vacancies.put(vacancy.getId(), vacancy);
    }
  }

  public List<VacancyDto> getJuniorVacancies() {
    return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase(Locale.ROOT).contains("junior")).toList();
  }

  public List<VacancyDto> getMiddleVacancies() {
    return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase(Locale.ROOT).contains("middle")).toList();
  }

  public List<VacancyDto> getSeniorVacancies() {
    return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase(Locale.ROOT).contains("senior")).toList();
  }

  public VacancyDto get(String id) {
    return vacancies.get(id);
  }
}
