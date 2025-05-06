package ru.yandex.practicum.filmorate.exception;

public class FilmIdException extends RuntimeException {
    public FilmIdException(int id) {
        super(String.format("Фильм с id %d не найден", id));
    }
}
