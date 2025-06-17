package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.exception.GenreIdException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository repository;

    public List<Genre> showAllGenres() {
        log.trace("Выведен список всех жанров");

        return repository.showAllGenres();
    }

    public Genre showGenreById(long genreId) {
        log.info("Выведен жанр c id {}", genreId);

        return checkGenre(genreId);
    }

    private Genre checkGenre(long genreId) {
        Optional<Genre> genreOpt = repository.showGenreById(genreId);
        if (genreOpt.isEmpty()) {
            throw new GenreIdException(genreId);
        }
        return genreOpt.get();
    }
}
