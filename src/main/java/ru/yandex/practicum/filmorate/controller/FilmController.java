package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<Film>> showAllFilms() {
        log.trace("Выведен список фильмов");
        return new ResponseEntity<>(filmService.showAllFilms(), HttpStatus.OK);
    }

    @GetMapping("/{filmId}")
    public ResponseEntity<Film> showFilm(@PathVariable("filmId") long filmId) {
        return new ResponseEntity<>(filmService.showFilm(filmId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody @Valid Film film) {
        log.info("Добавлен новый фильм под названием: {}, id: {}", film.getName(), film.getId());
        return new ResponseEntity<>(filmService.addFilm(film), HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<Film> updateFilm(@RequestBody @Valid Film film) {
        log.info("Обновлён фильм под названием: {}, id: {}", film.getName(), film.getId());
        return new ResponseEntity<>(filmService.updateFilm(film), HttpStatus.OK);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public ResponseEntity<Map<String, String>> addLike(@PathVariable("filmId") long filmId,
                                                       @PathVariable("userId") long userId) {
        return new ResponseEntity<>(filmService.addLikeToFilm(filmId, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public ResponseEntity<Map<String, String>> deleteLike(@PathVariable("filmId") long filmId,
                                                          @PathVariable("userId") long userId) {
        return new ResponseEntity<>(filmService.deleteLikeFromFilm(filmId, userId), HttpStatus.OK);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> showMostPopular(
            @RequestParam(name = "count",
                    required = false,
                    defaultValue = "10") int count) {
        return new ResponseEntity<>(filmService.showMostPopularFilms(count), HttpStatus.OK);
    }

}
