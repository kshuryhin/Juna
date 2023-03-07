package ua.pp.juna.vacanciesservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Parameter {
    CATEGORY("Category"),
    COUNTRY("Country"),
    SALARY_FROM("SalaryFrom"),
    SALARY_TO("SalaryTo"),
    GRADE("Grade"),
    EMPLOYMENT("EmploymentType"),
    ENGLISH_LEVEL("EnglishLevel");

    private final String value;
}
