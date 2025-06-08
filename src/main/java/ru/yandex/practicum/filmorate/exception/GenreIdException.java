package ru.yandex.practicum.filmorate.exception;

public class GenreIdException extends RuntimeException {
    public GenreIdException(long genreId) {
        super(String.format("Жанр с id %d не найден", genreId));
    }
}
