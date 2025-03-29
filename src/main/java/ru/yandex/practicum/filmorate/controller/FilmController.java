package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FieldValidationException;
import ru.yandex.practicum.filmorate.exception.IdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> allFilms = new HashMap<>();

    //Хотел назвать эту константу по правилам (капсом), но чекстайл не пропустил =D
    private final String incorrectFilmId = "Фильм с таким id не найден";

    @GetMapping
    public List<Film> showAllFilms() {
        log.trace("Выведен список фильмов");
        return allFilms.values().stream().toList();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validateFilm(film);
        film.setId(IDGenerator.getNextId(allFilms));
        allFilms.put(film.getId(), film);
        log.debug("Добавлен новый фильм под названием: {}, id: {}", film.getName(), film.getId());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (allFilms.containsKey(film.getId())) {
            validateFilm(film);
            allFilms.replace(film.getId(), film);
            log.debug("Обновлён фильм под названием: {}, id: {}", film.getName(), film.getId());
            return film;
        } else {
            log.info("Некорректный id: {}", film.getId());
            throw new IdException(incorrectFilmId);
        }
    }

    private void validateFilm(Film film) {
        if (film.getName().isBlank()) {
            log.info("Пустое название фильма: {}", film.getName());
            throw new FieldValidationException("Необходимо указать название фильма");
        } else if (film.getDescription().length() > 200) {
            log.info("Недопустимая длина описания: {}", film.getDescription().length());
            throw new FieldValidationException("Описание не должно быть длиннее 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Недопустимая дата выхода фильма: {}", film.getReleaseDate());
            throw new FieldValidationException("Дата выхода фильма не может быть раньше 28.12.1895");
        } else if (film.getDuration() < 0) {
            log.info("Недопустимая продолжительность фильма: {}", film.getDuration());
            throw new FieldValidationException("Продолжительность фильма не может быть отрицательной");
        }
    }

}
