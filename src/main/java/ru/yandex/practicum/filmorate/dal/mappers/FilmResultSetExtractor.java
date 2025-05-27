package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmResultSetExtractor implements ResultSetExtractor<Film> {

    @Override
    public Film extractData(ResultSet rs) throws SQLException, DataAccessException {
        rs.next();
        Film film = Film.builder()
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getLong("duration"))
                .mpa(new Mpa(rs.getLong("rating_id"), rs.getString("rating_name")))
                .build();
        film.setId(rs.getLong("film_id"));
        do {
            if (rs.getLong("genre_id") != 0) {
                film.getGenres().add(new Genre(
                        rs.getLong("genre_id"),
                        rs.getString("genre_name")));
            }
        } while (rs.next());
        return film;
    }
}
