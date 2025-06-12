package ru.yandex.practicum.filmorate.service;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.dal.MpaRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.exception.GenreIdException;
import ru.yandex.practicum.filmorate.exception.MpaIdException;
import ru.yandex.practicum.filmorate.exception.UserIdException;
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
        checkMpa(film.getMpa().getId());
        checkGenre(film.getGenres());
        filmRepository.updateFilm(film);
        return checkFilm(film.getId());
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

    public List<Film> showMostPopularFilms(@Positive int count) {
        log.trace("Выведен список самых понравившихся фильмов");
        return filmRepository.showMostPopularFilms(count);
    }

    private Film checkFilm(long filmId) {
        return filmRepository.showFilm(filmId)
                .stream()
                .findAny()
                .orElseThrow(() -> new FilmIdException(filmId));
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

    public List<Film> getCommonLikedFilms(@Positive long userId, @Positive long friendId) {
        checkUser(userId);
        checkUser(friendId);
        log.info("Запрошены общие фильмы пользователей {} и {}", userId, friendId);
        return filmRepository.getCommonLikedFilms(userId, friendId);
    }


}
