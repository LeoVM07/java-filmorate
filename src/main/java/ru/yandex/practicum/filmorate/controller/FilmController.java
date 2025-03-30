package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> allFilms = new HashMap<>();
    private int id = 1;

    @GetMapping
    public List<Film> showAllFilms() {
        log.trace("Выведен список фильмов");
        return allFilms.values().stream().toList();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(generateId());
        allFilms.put(film.getId(), film);
        log.debug("Добавлен новый фильм под названием: {}, id: {}", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (allFilms.containsKey(film.getId())) {
            allFilms.replace(film.getId(), film);
            log.debug("Обновлён фильм под названием: {}, id: {}", film.getName(), film.getId());
            return film;
        } else {
            log.error("Некорректный id: {}", film.getId());
            throw new IdException("Фильм с таким id не найден");
        }
    }

    private int generateId() {
        return id++;
    }

}
