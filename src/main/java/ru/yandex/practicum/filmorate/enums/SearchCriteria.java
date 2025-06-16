package ru.yandex.practicum.filmorate.enums;

import lombok.Getter;
import ru.yandex.practicum.filmorate.exception.SearchCriteriaException;

import java.util.Arrays;

@Getter
public enum SearchCriteria {
    DIRECTOR("director"),
    TITLE("title");

    private final String paramValue;

    SearchCriteria(String paramValue) {
        this.paramValue = paramValue;
    }

    public static SearchCriteria fromString(String value) {
        return Arrays.stream(SearchCriteria.values())
                .filter(e -> e.paramValue.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new SearchCriteriaException(
                        "Unknown search criteria: " + value
                ));
    }
}
