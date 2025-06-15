package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    List<Film> showAllFilms();

    Optional<Film> showFilm(long filmId);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(long filmID);

    void addLikeToFilm(long filmId, long userId);

    void deleteLikeFromFilm(long filmId, long userId);

    List<Film> showPopularFilmsByGenreYear(int count, Long genreId, Integer year);

    List<Film> showFilmsByDirector(long directorId, String sortFilmsBy);
}
