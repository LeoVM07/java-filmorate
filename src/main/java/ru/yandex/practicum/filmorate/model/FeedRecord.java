package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.Operation;

@Data
public class FeedRecord {

    private final long timestamp;
    private final long userId;
    private final EventType eventType;
    private final Operation operation;
    private long eventId;
    private final long entityId;
}
