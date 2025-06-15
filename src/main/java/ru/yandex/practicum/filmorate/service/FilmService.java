package ru.yandex.practicum.filmorate.service;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;


import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;
    private final MpaRepository mpaRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;

    public List<Film> showAllFilms() {
        return filmRepository.showAllFilms();
    }

    public Film showFilm(long filmId) {
        return checkFilm(filmId);
    }

    public Film addFilm(Film film) {
        checkGenre(film.getGenres());
        checkMpa(film.getMpa().getId());
        filmRepository.addFilm(film);

        return checkFilm(film.getId());
    }

    public Film updateFilm(Film film) {
        checkFilm(film.getId());
        checkMpa(film.getMpa().getId());
        checkGenre(film.getGenres());
        filmRepository.updateFilm(film);
        return checkFilm(film.getId());
    }

    public Map<String, String> deleteFilm(long filmId) {
        checkFilm(filmId);
        filmRepository.deleteFilm(filmId);
        log.info("Фильм с id {} был удалён из базы данных", filmId);
        return Map.of("result", String.format("fim with id %d was deleted", filmId));
    }

    public Map<String, String> addLikeToFilm(long filmId, long userId) {
        checkFilm(filmId);
        checkUser(userId);
        filmRepository.addLikeToFilm(filmId, userId);
        log.info("Фильму с id:{} был добавлен лайк от пользователя с id:{}", filmId, userId);
        return Map.of("result", String.format("like was added to film with id %d", filmId));

    }

    public Map<String, String> deleteLikeFromFilm(long filmId, long userId) {
        checkFilm(filmId);
        checkUser(userId);
        filmRepository.deleteLikeFromFilm(filmId, userId);
        log.info("У фильму с id:{} был удалён лайк от пользователя с id:{}", filmId, userId);
        return Map.of("result", String.format("like was removed from film with id %d", filmId));
    }

    public List<Film> showPopularFilmsByGenreYear(int count, Long genreId, Integer year) {
        if (count <= 0) {
            throw new CountException("Параметр count должен быть положительным числом");
        }

        if (genreId != null) {
            genreRepository.showGenreById(genreId)
                    .orElseThrow(() -> new GenreIdException(genreId));
        }

        log.trace("Выведен список популярных фильмов. count={}, genreId={}, year={}",
                count, genreId, year);
        return filmRepository.showPopularFilmsByGenreYear(count, genreId, year);
    }

    public List<Film> showFilmsByDirectorSorted(long directorId, String sortFilmsBy) {
        List<Film> filmsByDirector = filmRepository.showFilmsByDirector(directorId, sortFilmsBy);
        if (filmsByDirector.isEmpty()) {
            throw new DirectorIdException(directorId);
        }
        return filmsByDirector;
    }

    public List<Film> showCommonLikedFilms(@Positive long userId, @Positive long friendId) {
        checkUser(userId);
        checkUser(friendId);
        log.info("Запрошены общие фильмы пользователей {} и {}", userId, friendId);
        return filmRepository.showCommonLikedFilms(userId, friendId);
    }

    public List<Film> showRecommendedFilms(@Positive long userId) {
        checkUser(userId);
        log.info("Запрошенные рекомендации для userId={}", userId);
        List<Film> films = filmRepository.showRecommendedFilms(userId);
        log.info("Найдено {} рекомендуемых фильмов для userId={}", films.size(), userId);
        return films;
    }

    private Film checkFilm(long filmId) {
        try {
            return filmRepository.showFilm(filmId)
                    .stream()
                    .findAny()
                    .orElseThrow(() -> new FilmIdException(filmId));
        } catch (DataIntegrityViolationException e) {
            throw new FilmIdException(filmId);
        }
    }

    private void checkGenre(Set<Genre> genreSet) {
        for (Genre genre : genreSet) {
            genreRepository.showGenreById(genre.getId()).stream()
                    .findAny()
                    .orElseThrow(() -> new GenreIdException(genre.getId()));
        }
    }

    private void checkMpa(long ratingId) {
        mpaRepository.showRatingById(ratingId)
                .stream()
                .findAny()
                .orElseThrow(() -> new MpaIdException(ratingId));
    }

    private void checkUser(long userId) {
        userRepository.showUser(userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new UserIdException(userId));
    }

}
