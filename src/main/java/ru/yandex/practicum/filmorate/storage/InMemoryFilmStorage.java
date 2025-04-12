package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    @Getter
    private final Map<Integer, Film> allFilms = new HashMap<>();
    private int id = 1;

    @Override
    public List<Film> showAllFilms() {
        log.trace("Выведен список фильмов");
        return allFilms.values().stream().toList();
    }

    @Override
    public Film addFilm(@Valid Film film) {
        film.setId(generateId());
        allFilms.put(film.getId(), film);
        log.debug("Добавлен новый фильм под названием: {}, id: {}", film.getName(), film.getId());
        return film;
    }

    @Override
    public Film updateFilm(@Valid Film film) {
        if (allFilms.containsKey(film.getId())) {
            allFilms.replace(film.getId(), film);
            log.debug("Обновлён фильм под названием: {}, id: {}", film.getName(), film.getId());
            return film;
        } else {
            log.error("Некорректный id: {}", film.getId());
            throw new FilmIdException(film.getId());
        }
    }

    private int generateId() {
        return id++;
    }
}
