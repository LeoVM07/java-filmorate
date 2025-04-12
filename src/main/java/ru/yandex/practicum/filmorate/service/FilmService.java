package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.comparator.LikesAmountComparator;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@Slf4j
@Service
public class FilmService extends BasicService {

    @Autowired
    public FilmService(InMemoryUserStorage userStorage, InMemoryFilmStorage filmStorage) {
        super(userStorage, filmStorage);
    }

    public Film addLikeToFilm(int filmId, int userId) {
        Film film = checkFilm(filmId);
        checkUser(userId);

        film.getLikes().add(userId);
        log.info("Фильму с id:{} был добавлен лайк от пользователя с id:{}", filmId, userId);
        return film;
    }

    public Film deleteLikeFromFilm(int filmId, int userId) {
        Film film = checkFilm(filmId);
        checkUser(userId);

        film.getLikes().remove(userId);
        log.info("У фильму с id:{} был удалён лайк от пользователя с id:{}", filmId, userId);
        return film;
    }

    public List<Film> showMostPopularFilms(int count) {
        log.trace("Выведен список самых понравившихся фильмов");
        return getFilmStorage().getAllFilms().values().stream()
                .sorted(new LikesAmountComparator().reversed())
                .toList();
    }

}
