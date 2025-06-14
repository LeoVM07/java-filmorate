package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.FeedRecord;

import java.util.List;

public interface FeedRecordStorage {

    FeedRecord addFeedRecord(FeedRecord fr);

    List<FeedRecord> showFeedByUserId(long userId);
}
