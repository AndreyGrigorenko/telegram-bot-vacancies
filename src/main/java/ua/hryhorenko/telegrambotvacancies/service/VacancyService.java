package ua.hryhorenko.telegrambotvacancies.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ua.hryhorenko.telegrambotvacancies.dto.VacancyDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class VacancyService {
  private final Map<String, VacancyDto> vacancies = new HashMap<>();

  @PostConstruct
  public void init() {
    VacancyDto juniorMaDeveloper = new VacancyDto();
    juniorMaDeveloper.setId("1");
    juniorMaDeveloper.setTitle("Junior dev at MA");
    juniorMaDeveloper.setShortDescription("Java core is required!");
    vacancies.put("1", juniorMaDeveloper);

    VacancyDto middleMaDeveloper = new VacancyDto();
    middleMaDeveloper.setId("2");
    middleMaDeveloper.setTitle("Middle dev at MA");
    middleMaDeveloper.setShortDescription("Join our company!!");
    vacancies.put("2", middleMaDeveloper);

  }
}
