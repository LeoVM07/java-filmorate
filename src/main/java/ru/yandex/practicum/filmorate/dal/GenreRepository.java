package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> implements GenreStorage {
    public GenreRepository(JdbcTemplate jdbc, GenreRowMapper mapper) {
        super(jdbc);
        this.mapper = mapper;
    }

    private static final String SHOW_ALL_GENRES_QUERY = "SELECT * FROM genres";
    private static final String SHOW_RATING_BY_ID_QUERY = "SELECT * FROM genres WHERE genre_id = ?";


    @Override
    public List<Genre> showAllGenres() {
        return findMany(SHOW_ALL_GENRES_QUERY);
    }

    @Override
    public Optional<Genre> showGenreById(long genreId) {
        return findOne(SHOW_RATING_BY_ID_QUERY, genreId);
    }
}
