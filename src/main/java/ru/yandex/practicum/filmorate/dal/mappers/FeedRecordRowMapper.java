package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.enums.EventType;
import ru.yandex.practicum.filmorate.enums.Operation;
import ru.yandex.practicum.filmorate.model.FeedRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FeedRecordRowMapper implements RowMapper<FeedRecord> {

    @Override
    public FeedRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        FeedRecord fr = new FeedRecord(
                rs.getTimestamp("feed_timestamp").getTime(),
                rs.getLong("user_id"),
                EventType.valueOf(rs.getString("event_type")),
                Operation.valueOf(rs.getString("operation")),
                rs.getLong("entity_id")
        );
        fr.setEventId(rs.getLong("event_id"));
        return fr;
    }
}
