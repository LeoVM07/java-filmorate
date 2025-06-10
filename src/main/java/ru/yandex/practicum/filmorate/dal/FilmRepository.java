package ru.yandex.practicum.filmorate.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.FilmListResultSetExtractor;
import ru.yandex.practicum.filmorate.dal.mappers.FilmResultSetExtractor;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {

    private final FilmResultSetExtractor extractor;
    private final FilmListResultSetExtractor listExtractor;

    private static final String SHOW_ALL_FILMS_QUERY = """
            SELECT films.*,
            fg.genre_id,
            g.genre_name,
            mpa.rating_name
            FROM films
            LEFT JOIN film_genres fg ON films.film_id = fg.film_id
            LEFT JOIN genres g ON fg.genre_id = g.genre_id
            LEFT JOIN mpa_rating mpa ON films.rating_id = mpa.rating_id;
            """;
    private static final String SHOW_FILM_BY_ID_QUERY = """
            SELECT films.*,
            fg.genre_id,
            g.genre_name,
            mpa.rating_name,
            FROM films
            LEFT JOIN film_genres AS fg ON films.film_id = fg.film_id
            LEFT JOIN genres AS g ON fg.genre_id = g.genre_id
            LEFT JOIN mpa_rating AS mpa ON films.rating_id = mpa.rating_id
            WHERE films.film_id = ?;
            """;

    private static final String ADD_FILM_QUERY = """
            INSERT INTO films (name,
            description,
            release_date,
            duration,
            rating_id)
            VALUES(?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_FILM_QUERY = """
            UPDATE films SET name = ?,
            description = ?,
            release_date = ?,
            duration = ?,
            rating_id = ?
            WHERE film_id = ?;
            """;
    private static final String ADD_FILM_GENRE_QUERY = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String DELETE_FILM_GENRE_QUERY = "DELETE film_genres WHERE film_id = ?";
    private static final String ADD_LIKE_TO_FILM_QUERY = "INSERT INTO likes(film_id, user_id) VALUES(?, ?);";
    private static final String DELETE_LIKE_FROM_FILM_QUERY = "DELETE likes WHERE film_id = ? AND user_id = ?";
    private static final String GET_POPULAR_FILMS_BY_GENRE_YEAR_QUERY = """
    SELECT films.*,
    fg.genre_id,
    g.genre_name,
    mpa.rating_name
    FROM (
        SELECT likes.film_id, COUNT(likes.like_id) AS count_likes
        FROM likes
        LEFT JOIN films ON likes.film_id = films.film_id
        LEFT JOIN film_genres fg ON likes.film_id = fg.film_id
        WHERE (? IS NULL OR fg.genre_id = ?)
        AND (? IS NULL OR EXTRACT(YEAR FROM films.release_date) = ?)
        GROUP BY likes.film_id
        ORDER BY COUNT(likes.like_id) DESC
        LIMIT ?
    ) AS l
    LEFT JOIN films ON l.film_id = films.film_id
    LEFT JOIN film_genres AS fg ON films.film_id = fg.film_id
    LEFT JOIN genres AS g ON fg.genre_id = g.genre_id
    LEFT JOIN mpa_rating AS mpa ON films.rating_id = mpa.rating_id;
    """;

    @Autowired
    public FilmRepository(JdbcTemplate jdbc,
                          FilmResultSetExtractor extractor,
                          FilmListResultSetExtractor listExtractor) {
        super(jdbc);
        this.extractor = extractor;
        this.listExtractor = listExtractor;
    }

    @Override
    public List<Film> showAllFilms() {
        return extractMany(SHOW_ALL_FILMS_QUERY, listExtractor);
    }

    @Override
    public Optional<Film> showFilm(long filmId) {
        return extractOne(SHOW_FILM_BY_ID_QUERY, extractor, filmId);
    }

    @Override
    public Film addFilm(Film film) {
        long filmId = insert(
                ADD_FILM_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(filmId);
        for (Genre genre : film.getGenres()) {
            try {
                insert(ADD_FILM_GENRE_QUERY, filmId, genre.getId());
            } catch (DuplicateKeyException ignored) {
            }
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        delete(DELETE_FILM_GENRE_QUERY, film.getId());
        update(
                UPDATE_FILM_QUERY,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()).toString(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
        for (Genre genre : film.getGenres()) {
            try {
                insert(ADD_FILM_GENRE_QUERY, film.getId(), genre.getId());
            } catch (DuplicateKeyException ignored) {
            }
        }
        return film;
    }

    @Override
    public void addLikeToFilm(long filmId, long userId) {
        insert(ADD_LIKE_TO_FILM_QUERY, filmId, userId);
    }

    @Override
    public void deleteLikeFromFilm(long filmId, long userId) {
        delete(DELETE_LIKE_FROM_FILM_QUERY, filmId, userId);
    }

    @Override
    public List<Film> getPopularFilmsByGenreYear(int count, Long genreId,Integer year) {
        return extractMany(GET_POPULAR_FILMS_BY_GENRE_YEAR_QUERY, listExtractor,   genreId, genreId, year, year, count);
    }
}
