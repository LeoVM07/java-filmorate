package ru.yandex.practicum.filmorate.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDirectorIdException(final DirectorIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка в id режиссёра", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmIdException(final FilmIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка в id фильма", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserIdException(final UserIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка в id пользователя", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerException(final InternalServerException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка на стороне сервера", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaIdException(final MpaIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка в указанном id рейтинга", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenreIdException(final GenreIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Ошибка в указанном id жанра", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDuplicateUserIdException(final DuplicateUserIdException e) {
        log.error(e.getMessage());
        return new ErrorResponse("Повторное введение id", e.getMessage());
    }

    //класс для формирования ответа при обработке исключений
    @Getter
    public static class ErrorResponse {
        private final String error;
        private final String description;

        private ErrorResponse(String error, String description) {
            this.error = error;
            this.description = description;
        }
    }
}
