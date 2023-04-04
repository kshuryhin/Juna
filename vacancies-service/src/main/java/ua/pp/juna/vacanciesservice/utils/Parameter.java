package ua.pp.juna.vacanciesservice.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Parameter {
    CATEGORY("category"),
    COUNTRY("country"),
    SALARY_FROM("salaryFrom"),
    SALARY_EXPECTATIONS("salaryExpectations"),
    GRADE("grade"),
    EMPLOYMENT("employmentType"),
    ENGLISH_LEVEL("englishLevel");

    private final String value;
}
