package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.MpaRowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<Mpa> implements RatingStorage {

    public MpaRepository(JdbcTemplate jdbc, MpaRowMapper mapper) {
        super(jdbc);
        this.mapper = mapper;
    }

    private static final String SHOW_ALL_RATINGS_QUERY = "SELECT * FROM mpa_rating";
    private static final String SHOW_RATING_BY_ID_QUERY = "SELECT * FROM mpa_rating WHERE rating_id = ?";


    @Override
    public List<Mpa> showALlRatings() {
        return findMany(SHOW_ALL_RATINGS_QUERY);
    }

    @Override
    public Optional<Mpa> showRatingById(long ratingId) {
        return findOne(SHOW_RATING_BY_ID_QUERY, ratingId);
    }
}
