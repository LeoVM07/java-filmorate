package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FeedRecordRowMapper;
import ru.yandex.practicum.filmorate.model.FeedRecord;
import ru.yandex.practicum.filmorate.storage.FeedRecordStorage;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class FeedRecordRepository extends BaseRepository<FeedRecord> implements FeedRecordStorage{

    private static final String ADD_FEED_QUERY = """
            INSERT INTO feed (feed_timestamp, user_id, event_type, operation, entity_id)
            VALUES(?, ?, ?, ?, ?);
            """;
    private static final String SHOW_FEED_BY_USER_ID_QUERY = "SELECT * FROM feed WHERE user_id =?";

    public FeedRecordRepository(JdbcTemplate jdbc, FeedRecordRowMapper mapper) {
        super(jdbc);
        this.mapper = mapper;
    }

    @Override
    public FeedRecord addFeedRecord(FeedRecord fr) {
        long eventId = insert(
                ADD_FEED_QUERY,
                new Timestamp(fr.getTimestamp()),
                fr.getUserId(),
                fr.getEventType().toString(),
                fr.getOperation().toString(),
                fr.getEntityId());
        fr.setEventId(eventId);
        return fr;
    }

    @Override
    public List<FeedRecord> showFeedByUserId(long userId) {
        return findMany(SHOW_FEED_BY_USER_ID_QUERY, userId);
    }
}
