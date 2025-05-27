package ru.yandex.practicum.filmorate.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super("Пользователь не может добавить в друзья сам себя");
    }
}
