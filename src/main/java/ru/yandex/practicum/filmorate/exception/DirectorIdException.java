package ru.yandex.practicum.filmorate.exception;

public class DirectorIdException extends RuntimeException {
    public DirectorIdException(long id) {
        super(String.format("Пользователь с id %d не найден", id));
    }
}
