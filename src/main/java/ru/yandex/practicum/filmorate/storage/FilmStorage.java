package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    public List<Film> showAllFilms();

    public Optional<Film> showFilm(long filmId);

    public Film addFilm(Film film);

    public Film updateFilm(Film film);

    public void addLikeToFilm(long filmId, long userId);

    public void deleteLikeFromFilm(long filmId, long userId);

    public List<Film> showMostPopularFilms(int count);

    public List<Film> getLikedFilmsByUser(long userId);

    public List<Film> getCommonLikedFilms(long userId, long friendId);

    public int getLikesCount(long filmId);

}
