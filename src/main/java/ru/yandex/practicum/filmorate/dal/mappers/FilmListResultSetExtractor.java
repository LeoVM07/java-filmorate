package ru.yandex.practicum.filmorate.dal.mappers;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class FilmListResultSetExtractor implements ResultSetExtractor<List<Film>> {

    @Override
    public List<Film> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Long, Film> idToFilm = new LinkedHashMap<>();
        while (rs.next()) {
            Long currentId = rs.getLong("film_id");
            Film film;
            if (idToFilm.containsKey(currentId)) {
                film = idToFilm.get(currentId);
            } else {
                film = Film.builder()
                        .name(rs.getString("name"))
                        .description(rs.getString("description"))
                        .releaseDate(rs.getDate("release_date").toLocalDate())
                        .duration(rs.getLong("duration"))
                        .mpa(new Mpa(rs.getLong("rating_id"), rs.getString("rating_name")))
                        .build();
                film.setId(currentId);
                idToFilm.put(currentId, film);
            }
            if (rs.getInt("genre_id") != 0) {
                film.getGenres().add(new Genre(
                        rs.getLong("genre_id"),
                        rs.getString("genre_name")));
            }
        }
        return idToFilm.values().stream().toList();
    }
}