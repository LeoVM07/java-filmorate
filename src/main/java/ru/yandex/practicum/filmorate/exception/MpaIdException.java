package ru.yandex.practicum.filmorate.exception;

public class MpaIdException extends RuntimeException {
    public MpaIdException(long ratingId) {
        super(String.format("Рейтинга с id %d не существует", ratingId));
    }
}
