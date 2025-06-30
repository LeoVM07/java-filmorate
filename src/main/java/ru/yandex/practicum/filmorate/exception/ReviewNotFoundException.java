package ru.yandex.practicum.filmorate.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(long reviewId) {
            super(String.format("Отзыв с id %d не существует", reviewId));
        }
}
