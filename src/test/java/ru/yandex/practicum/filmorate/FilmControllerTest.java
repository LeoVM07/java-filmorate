package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    FilmStorage filmStorage;

    @BeforeEach
    void createFilmController() {
        this.filmStorage = new InMemoryFilmStorage();
    }

    @Test
    void filmsShouldBeEqual() {
        Film film1 = new Film("Film", "Description", LocalDate.of(2001, 4, 15),
                90);
        film1.setId(1);
        Film film2 = filmStorage.addFilm(film1);
        assertEquals(film1, film2, "В памяти должен храниться тот же фильм, " +
                "что был добавлен");
    }

    @Test
    void listOfFilmsShouldBeEqual() {
        Film film1 = new Film("Film1", "Description1", LocalDate.of(2001, 4, 15),
                90);
        Film film2 = new Film("Film2", "Description2", LocalDate.of(1993, 7, 11),
                130);
        List<Film> listOfFilms1 = List.of(film1, film2);

        filmStorage.addFilm(film1);
        filmStorage.addFilm(film2);
        List<Film> listOfFilms2 = filmStorage.showAllFilms();

        assertEquals(listOfFilms1, listOfFilms2, "Списки фильмов должны быть одинаковыми");
    }

}
