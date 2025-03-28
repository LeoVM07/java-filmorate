package ru.yandex.practicum.filmorate.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.FieldValidationException;

@RestControllerAdvice
public class FieldValidationHandler {

    @ExceptionHandler(FieldValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullFiledException(FieldValidationException e) {
        return e.getMessage();
    }
}
