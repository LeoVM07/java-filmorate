package ru.yandex.practicum.filmorate.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.IdException;

@RestControllerAdvice
public class IdNotFoundHandler {

    @ExceptionHandler(IdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleIdException(IdException e) {
        return e.getMessage();
    }
}
