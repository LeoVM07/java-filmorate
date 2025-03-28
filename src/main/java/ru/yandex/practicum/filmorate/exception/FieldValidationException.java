package ru.yandex.practicum.filmorate.exception;

public class FieldValidationException extends RuntimeException {
    public FieldValidationException(String message) {
        super(message);
    }
}
