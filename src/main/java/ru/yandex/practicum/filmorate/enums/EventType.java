package ru.yandex.practicum.filmorate.enums;

import lombok.Getter;

@Getter
public enum EventType {
    LIKE("like"),
    REVIEW("review"),
    FRIEND("friend");

    private final String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }
}
