package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.exception.UserIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

@Slf4j
@Getter
@Service
public class BasicService {
    private final InMemoryUserStorage userStorage;
    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public BasicService(InMemoryUserStorage userStorage, InMemoryFilmStorage filmStorage) {
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    //Метод для проверки наличия пользователя в списке
    protected User checkUser(int userId) {
        if (userStorage.getAllUsers().containsKey(userId)) {
            log.trace("Пользователь с id: {} найден", userId);
            return userStorage.getAllUsers().get(userId);
        } else {
            throw new UserIdException(userId);
        }
    }

    //Метод для проверки наличия фильма в списке
    protected Film checkFilm(int filmId) {
        if (filmStorage.getAllFilms().containsKey(filmId)) {
            log.trace("Фильм с id: {} найден", filmId);
            return filmStorage.getAllFilms().get(filmId);
        } else {
            throw new FilmIdException(filmId);
        }
    }
}
