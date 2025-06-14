package ru.yandex.practicum.filmorate.enums;

import lombok.Getter;

@Getter
public enum Operation {

    REMOVE("remove"),
    ADD("add"),
    UPDATE("update");

    private final String operationType;

    Operation(String operationType) {
        this.operationType = operationType;
    }
}
