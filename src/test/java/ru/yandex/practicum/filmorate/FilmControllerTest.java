package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.FieldValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    FilmController filmController;

    @BeforeEach
    void createFilmController() {
        this.filmController = new FilmController();
    }

    @Test
    void filmsShouldBeEqual() {
        Film film1 = new Film("Film", "Description", LocalDate.of(2001, 4, 15),
                Duration.ofMinutes(90));
        film1.setId(1);
        Film film2 = filmController.addFilm(film1);
        assertEquals(film1, film2, "В памяти должен храниться тот же фильм, " +
                "что был добавлен");
    }

    @Test
    void listOfFilmsShouldBeEqual() {
        Film film1 = new Film("Film1", "Description1", LocalDate.of(2001, 4, 15),
                Duration.ofMinutes(90));
        Film film2 = new Film("Film2", "Description2", LocalDate.of(1993, 7, 11),
                Duration.ofMinutes(130));
        List<Film> listOfFilms1 = List.of(film1, film2);

        filmController.addFilm(film1);
        filmController.addFilm(film2);
        List<Film> listOfFilms2 = filmController.showAllFilms();

        assertEquals(listOfFilms1, listOfFilms2, "Списки фильмов должны быть одинаковыми");
    }

    @Test
    void shouldThrowFieldValidationExceptionDescriptionLength() {
        Film film = new Film("Film", "*".repeat(201), LocalDate.of(2001, 4, 15),
                Duration.ofMinutes(90));

        FieldValidationException e = assertThrows(FieldValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Описание не должно быть длиннее 200 символов", e.getMessage(),
                "Некорректный формат сообщения ошибки");
    }

    @Test
    void filmCorrectReleaseDateTest() {
        Film film1 = new Film("Film", "Description", LocalDate.of(1895, 12, 28),
                Duration.ofMinutes(90));
        assertDoesNotThrow(() -> filmController.addFilm(film1));

        Film film2 = new Film("Film", "Description", LocalDate.of(1895, 12, 28)
                .minusDays(1),
                Duration.ofMinutes(90));
        FieldValidationException e = assertThrows(FieldValidationException.class, () -> filmController.addFilm(film2));
        assertEquals("Дата выхода фильма не может быть раньше 28.12.1895", e.getMessage(),
                "Некорректный формат сообщения ошибки");
    }

    @Test
    void durationShouldNotBeNegativeTest() {
        Film film = new Film("Film", "Description", LocalDate.of(1895, 12, 28),
                Duration.ofMinutes(-90));
        FieldValidationException e = assertThrows(FieldValidationException.class, () -> filmController.addFilm(film));
        assertEquals("Продолжительность фильма не может быть отрицательной", e.getMessage(),
                "Некорректный формат сообщения ошибки");
    }

}
