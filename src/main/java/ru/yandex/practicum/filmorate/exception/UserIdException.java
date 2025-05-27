package ru.yandex.practicum.filmorate.exception;

public class UserIdException extends RuntimeException {
    public UserIdException(long id) {
        super(String.format("Пользователь с id %d не найден", id));
    }
}
