package ru.yandex.practicum.filmorate.exception;

public class SearchFailException extends RuntimeException {
    public SearchFailException(String message) {
        super(message);
    }
}
