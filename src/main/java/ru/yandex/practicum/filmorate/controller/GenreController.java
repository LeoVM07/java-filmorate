package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> showAllGenres() {
        return new ResponseEntity<>(genreService.showAllGenres(), HttpStatus.OK);
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> showGenreByID(@PathVariable("genreId") long genreId) {
        return new ResponseEntity<>(genreService.showGenreById(genreId), HttpStatus.OK);
    }
}
