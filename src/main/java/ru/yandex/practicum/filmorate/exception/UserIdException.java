package ru.yandex.practicum.filmorate.exception;

public class UserIdException extends RuntimeException {
    public UserIdException(int id) {
        super(String.format("Пользователь с id %d не найден", id));
    }
}
